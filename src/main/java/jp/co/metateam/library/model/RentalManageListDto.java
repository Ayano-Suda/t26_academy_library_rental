package jp.co.metateam.library.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor

// このDTOは貸出一覧画面へ表示する情報を保持する箱
@Data
public class RentalManageListDto {

    // 貸出管理番号
    private Long id;

    // 書籍名
    private String bookTitle;

    // 在庫管理番号
    private String stockId;

    // 貸出ステータス
    private Integer status;

    // 利用者
    private String accountName;

    // 貸出予定日
    private LocalDate expectedRentalOn;

    // 貸出開始日
    private LocalDateTime rentaledAt;

    // 返却予定日
    private LocalDate expectedReturnOn;

    // 返却日
    private LocalDateTime returnedAt;

    // キャンセル日
    private LocalDateTime canceledAt;
}