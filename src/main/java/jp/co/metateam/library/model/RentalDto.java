//この以下dtoはhtmlの入力値を受け取る箱
package jp.co.metateam.library.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import jakarta.validation.constraints.AssertTrue;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import jakarta.validation.constraints.AssertTrue;

//以下入力されていなかったらエラーを表示している
@Data
public class RentalDto {
    // 必須項目・形式チェック
    @NotBlank(message = "社員番号を入力してください")
    private String employeeId;

    @NotBlank(message = "貸出予定日を入力してください")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "貸出予定日の値はyyyy/MM/dd形式のみで入力してください。")
    private String expectedRentalOn;

    @NotBlank(message = "返却予定日を入力してください")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "返却予定日の値はyyyy/MM/dd形式のみで入力してください。")
    private String expectedReturnOn;

    @NotBlank(message = "在庫管理番号を入力してください")
    private String stockId;

    @NotNull(message = "貸出ステータスを選択してください")
    private Integer status;

    // 以下は画面で選ばれたステータスが返却済み・キャンセルなら保存させないチェック
    @AssertTrue(message = "返却済みまたはキャンセル済みのデータは更新できません。")
    public boolean isStatusUpdatable() {
        if (status == null) {
            return true;
        }

        return status != 2 && status != 3;
    }

    // 貸出予定日を未来日で貸出ステータスが貸出中を選択するエラー
    @AssertTrue(message = "未来日を貸出予定日として設定する場合は、「貸出待ち」を選択してください。")
    public boolean isFutureRentalStatusValid() {

        // 未入力時は他バリデーションに任せる
        if (expectedRentalOn == null || expectedRentalOn.isBlank() || status == null) {
            return true;
        }

        LocalDate rentalDate = LocalDate.parse(
                expectedRentalOn,
                DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // 貸出予定日が未来日 && ステータスが貸出中
        if (rentalDate.isAfter(LocalDate.now()) && status == 1) {
            return false;
        }

        return true;
    }

    // 貸出中予定日が過去日で、「貸出中」になっているかチェック
    @AssertTrue(message = "過去日の貸出予定日は、「貸出中」のみ選択可能です。")
    public boolean isPastRentalStatusValid() {

        if (expectedRentalOn == null || expectedRentalOn.isEmpty() || status == null) {
            return true;
        }

        LocalDate rentalDate = LocalDate.parse(expectedRentalOn);

        return !rentalDate.isBefore(LocalDate.now()) || status == 1;
    }
}