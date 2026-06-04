package jp.co.metateam.library.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.metateam.library.model.RentalDto;
import jp.co.metateam.library.model.RentalManage;
import jp.co.metateam.library.model.RentalManageListDto;
import jp.co.metateam.library.repository.RentalRepository;

@Service
public class RentalService {

    private final RentalRepository rentalRepository;

    public RentalService(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    public void save(RentalDto rentalDto) {

        RentalManage rentalManage = new RentalManage();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        rentalManage.setEmployeeId(rentalDto.getEmployeeId());
        rentalManage.setStockId(rentalDto.getStockId());
        rentalManage.setStatus(rentalDto.getStatus());

        rentalManage.setExpectedRentalOn(
                LocalDate.parse(rentalDto.getExpectedRentalOn(), formatter));

        rentalManage.setExpectedReturnOn(
                LocalDate.parse(rentalDto.getExpectedReturnOn(), formatter));

        rentalRepository.save(rentalManage);
    }

    /**
     * 在庫管理番号に紐づく貸出データ取得
     */
    @Transactional
    public List<RentalManage> findByStockId(String stockId) {

        // RENTAL_MANAGEテーブルから在庫管理番号に一致する貸出情報を取得
        return rentalRepository.findByStockId(stockId);
    }

    /**
     * 貸出一覧画面に表示する情報を取得
     */
    @Transactional(readOnly = true)
    public List<RentalManageListDto> findRentalManageList() {

        // Repositoryで複数テーブルをJOINし、一覧画面用DTOとして取得する
        return rentalRepository.findRentalManageList();
    }
}