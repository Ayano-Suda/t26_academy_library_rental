package jp.co.metateam.library.controller;

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
import jp.co.metateam.library.model.Account;
import jp.co.metateam.library.model.RentalDto;
import jp.co.metateam.library.model.Stock;
import jp.co.metateam.library.service.AccountService;
import jp.co.metateam.library.service.RentalService;
import jp.co.metateam.library.service.StockService;
import lombok.extern.log4j.Log4j2;

/**
 * 貸出管理関連クラスß
 */
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
     * 貸出一覧画面
     */
    @GetMapping("/rental/index")
    public String index(Model model) {

        return "/rental/index";
    }

    /**
     * 貸出登録画面初期表示
     */
    @GetMapping("/rental/add")
    public String add(Model model) {

        setPullDownData(model);

        model.addAttribute("rentalManageDto", new RentalDto());

        return "rental/add";
    }

    /**
     * 保存処理
     */
    @PostMapping("/rental/save")
    public String save(
            @Valid @ModelAttribute("rentalManageDto") RentalDto rentalDto,
            BindingResult bindingResult,
            Model model) {

        // バリデーションエラーがある場合
        if (bindingResult.hasErrors()) {

            // プルダウン再設定
            setPullDownData(model);

            // 入力画面へ戻す
            return "rental/add";
        }

        // 保存処理
        rentalService.save(rentalDto);

        // 一覧へリダイレクト
        return "redirect:/rental/index";
    }

    /**
     * プルダウン共通設定
     */
    private void setPullDownData(Model model) {

        // 社員一覧取得
        List<Account> accountList = accountService.findAll();

        // 貸出可能在庫一覧取得
        List<Stock> stockList = stockService.findStockAvailableAll();

        // 貸出ステータスプルダウン
        List<Map<String, Object>> rentalStatusList = new ArrayList<>();

        rentalStatusList.add(Map.of("value", 0, "text", "貸出待ち"));
        rentalStatusList.add(Map.of("value", 1, "text", "貸出中"));
        rentalStatusList.add(Map.of("value", 2, "text", "返却済み"));
        rentalStatusList.add(Map.of("value", 3, "text", "キャンセル"));

        // 画面へ渡す
        model.addAttribute("accounts", accountList);
        model.addAttribute("stockList", stockList);
        model.addAttribute("rentalStatus", rentalStatusList);
    }
}