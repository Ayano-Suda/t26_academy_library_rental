package jp.co.metateam.library.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jp.co.metateam.library.model.RentalManage;
import jp.co.metateam.library.model.RentalManageListDto;

@Repository
public interface RentalRepository
                extends JpaRepository<RentalManage, Long> {

        /**
         * 在庫管理番号に紐づく貸出データ取得
         */
        List<RentalManage> findByStockId(String stockId);

        /**
         * 貸出一覧画面に表示する情報を取得
         * RENTAL_MANAGEを中心に、STOCK・BOOK_MST・ACCOUNTをJOINして
         * 一覧画面用DTOに必要な情報をまとめて取得する
         */
        @Query("""
                            SELECT new jp.co.metateam.library.model.RentalManageListDto(
                                r.id,
                                b.title,
                                s.id,
                                r.status,
                                a.name,
                                r.expectedRentalOn,
                                r.rentaledAt,
                                r.expectedReturnOn,
                                r.returnedAt,
                                r.canceledAt
                            )
                            FROM RentalManage r
                            JOIN Stock s ON r.stockId = s.id
                            JOIN s.bookMst b
                            JOIN Account a ON r.employeeId = a.employeeId
                            ORDER BY r.id
                        """)
        List<RentalManageListDto> findRentalManageList();
}