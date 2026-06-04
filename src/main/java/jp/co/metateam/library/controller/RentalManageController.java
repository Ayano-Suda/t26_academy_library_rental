package jp.co.metateam.library.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;
import jp.co.metateam.library.constants.Constants;
import jp.co.metateam.library.model.Account;
import jp.co.metateam.library.model.RentalDto;
import jp.co.metateam.library.model.RentalManage;
import jp.co.metateam.library.model.RentalManageListDto;
import jp.co.metateam.library.model.Stock;
import jp.co.metateam.library.service.AccountService;
import jp.co.metateam.library.service.RentalService;
import jp.co.metateam.library.service.StockService;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
public class RentalManageController {

    private final AccountService accountService;
    private final StockService stockService;
    private final RentalService rentalService;

    public RentalManageController(
            AccountService accountService,
            StockService stockService,
            RentalService rentalService) {

        this.accountService = accountService;
        this.stockService = stockService;
        this.rentalService = rentalService;
    }

    /**
     * 貸出一覧画面表示
     */
    @GetMapping("/rental/index")
    public String index(Model model) {

        // 貸出一覧画面に表示する情報を取得
        List<RentalManageListDto> rentalManageList = rentalService.findRentalManageList();

        // HTMLで使用できるようModelへ格納
        model.addAttribute("rentalManageList", rentalManageList);

        // 貸出一覧画面表示
        return "rental/index";
    }

    @GetMapping("/rental/add")
    public String add(Model model) {
        setPullDownData(model);
        model.addAttribute("rentalManageDto", new RentalDto());
        return "rental/add";
    }

    @PostMapping("/rental/save")
    public String save(
            @Valid @ModelAttribute("rentalManageDto") RentalDto rentalDto,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            setPullDownData(model);
            return "rental/add";
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate expectedRentalOn = LocalDate.parse(rentalDto.getExpectedRentalOn(), formatter);
        LocalDate expectedReturnOn = LocalDate.parse(rentalDto.getExpectedReturnOn(), formatter);

        Stock stock = stockService.findById(rentalDto.getStockId());

        if (expectedReturnOn.isBefore(expectedRentalOn)) {
            bindingResult.rejectValue(
                    "expectedReturnOn",
                    "date.error",
                    "「返却予定日」は「貸出予定日」以降の日付を入力してください。");
        }

        if (expectedRentalOn.isAfter(LocalDate.now())) {
            if (rentalDto.getStatus() != 0) {
                bindingResult.rejectValue(
                        "status",
                        "status.error",
                        "未来日を貸出予定日として設定する場合は、「貸出待ち」を選択してください。");
            }
        }

        if (stock == null) {
            bindingResult.rejectValue(
                    "stockId",
                    "error.stockId",
                    "存在しない在庫管理番号です");
        } else {
            if (stock.getStatus() != Constants.STOCK_AVAILABLE) {
                bindingResult.rejectValue(
                        "stockId",
                        "error.stockId",
                        "この本は貸出できません");
            }

            List<RentalManage> rentalList = rentalService.findByStockId(rentalDto.getStockId());

            for (RentalManage rentalManage : rentalList) {

                LocalDate registeredRentalOn = rentalManage.getExpectedRentalOn();
                LocalDate registeredReturnOn = rentalManage.getExpectedReturnOn();

                if (expectedRentalOn.isBefore(registeredReturnOn)
                        && expectedReturnOn.isAfter(registeredRentalOn)) {

                    bindingResult.rejectValue(
                            "stockId",
                            "rental.period.duplicate",
                            "この本は指定された期間に既に貸出予定があります。");

                    break;
                }
            }
        }

        // エラーが1件でもある場合
        if (bindingResult.hasErrors()) {

            // プルダウン情報を再設定
            setPullDownData(model);

            // 登録画面へ戻る
            return "rental/add";
        }

        // 貸出情報をRentalテーブルへ保存
        rentalService.save(rentalDto);

        // 一覧画面へ戻る
        return "redirect:/rental/index";
    }

    private void setPullDownData(Model model) {

        List<Account> accountList = accountService.findAll();
        List<Stock> stockList = stockService.findStockAvailableAll();

        List<Map<String, Object>> rentalStatusList = new ArrayList<>();

        rentalStatusList.add(Map.of("value", 0, "text", "貸出待ち"));
        rentalStatusList.add(Map.of("value", 1, "text", "貸出中"));
        rentalStatusList.add(Map.of("value", 2, "text", "返却済み"));
        rentalStatusList.add(Map.of("value", 3, "text", "キャンセル"));

        model.addAttribute("accounts", accountList);
        model.addAttribute("stockList", stockList);
        model.addAttribute("rentalStatus", rentalStatusList);
    }
}