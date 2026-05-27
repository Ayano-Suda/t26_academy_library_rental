package jp.co.metateam.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.co.metateam.library.model.RentalManage;
import java.util.List;
import jp.co.metateam.library.model.RentalManage;

@Repository
public interface RentalRepository
                extends JpaRepository<RentalManage, Long> {
        /**
         * 在庫管理番号に紐づく貸出データ取得
         */
        List<RentalManage> findByStockId(String stockId);
}