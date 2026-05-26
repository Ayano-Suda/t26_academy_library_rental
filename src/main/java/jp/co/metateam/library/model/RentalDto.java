//この以下dtoはhtmlの入力値を受け取る箱
package jp.co.metateam.library.model;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

//以下入力されていなかったらエラーを表示している
@Data
public class RentalDto {

    @NotBlank(message = "社員番号を入力してください")
    private String employeeId;

    @NotNull(message = "貸出予定日を入力してください")
    private LocalDate expectedRentalOn;

    @NotNull(message = "返却予定日を入力してください")
    private LocalDate expectedReturnOn;

    @NotBlank(message = "在庫管理番号を入力してください")
    private String stockId;

    @NotNull(message = "貸出ステータスを選択してください")
    private Integer status;
}