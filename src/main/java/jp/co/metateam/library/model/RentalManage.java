package jp.co.metateam.library.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

/**
 * 貸出管理
 */
@Entity // EntityにこのクラスはDBテーブルと対応しますと教えている
@Table(name = "rental_manage") // RentalManageクラス ↔ RentalManageテーブル
public class RentalManage {
    @Id // 主キー宣言
    @GeneratedValue(strategy = GenerationType.IDENTITY) // GeneratedValue＝DB側で自動採番してください
    @Column(name = "id")
    private Long id;

    /** 在庫管理番号 */
    @Column(name = "stock_id", nullable = false)
    private String stockId;
    // null禁止：nullable = false。unique = true：重複禁止。

    /** 書籍タイトル */
    @Transient
    private String title;

    /** 社員番号 */
    @Column(name = "employee_id")
    private String employeeId;

    /** 貸出ステータス */
    @Column(name = "status")
    private Integer status;

    /** 貸出予定日 */
    @Column(name = "expected_rental_on")
    private LocalDate expectedRentalOn;

    /** 返却予定日 */
    @Column(name = "expected_return_on")
    private LocalDate expectedReturnOn;

    /** 貸出日時 */
    @Column(name = "rentaled_at")
    private LocalDateTime rentaledAt;

    /** 返却日時 */
    @Column(name = "returned_at")
    private LocalDateTime returnedAt;

    /** キャンセル日時 */
    @Column(name = "canceled_at")
    private LocalDateTime canceledAt;

    /** Getters */
    // 値を取り出す。箱から取り出す。

    public Long getId() {
        return this.id;
    }

    public String getStockId() {
        return this.stockId;
    }

    public String getTitle() {
        return this.title;
    }

    public String getEmployeeId() {
        return this.employeeId;
    }

    public Integer getStatus() {
        return this.status;
    }

    public LocalDate getExpectedRentalOn() {
        return this.expectedRentalOn;
    }

    public LocalDate getExpectedReturnOn() {
        return this.expectedReturnOn;
    }

    public LocalDateTime getRentedAt() {
        return this.rentaledAt;
    }

    public LocalDateTime getReturnedAt() {
        return this.returnedAt;
    }

    public LocalDateTime getCanceledAt() {
        return this.canceledAt;
    }

    /** Setters */
    // 値を入れる。箱へ入れる。

    public void setId(Long id) {
        this.id = id;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setExpectedRentalOn(LocalDate expectedRentalOn) {
        this.expectedRentalOn = expectedRentalOn;
    }

    public void setExpectedReturnOn(LocalDate expectedReturnOn) {
        this.expectedReturnOn = expectedReturnOn;
    }

    public void setRentedAt(LocalDateTime rentedAt) {
        this.rentaledAt = rentedAt;
    }

    public void setReturnedAt(LocalDateTime returnedAt) {
        this.returnedAt = returnedAt;
    }

    public void setCanceledAt(LocalDateTime canceledAt) {
        this.canceledAt = canceledAt;
    }
}
