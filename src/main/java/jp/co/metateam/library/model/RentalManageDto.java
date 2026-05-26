package jp.co.metateam.library.model;

import java.security.Timestamp;
import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

/**
 * 貸出登録DTO
 */
@Getter
@Setter
public class RentalManageDto {

    @NotBlank(message = "社員番号は必須です")
    private String employeeId;

    @NotNull(message = "貸出予定日は必須です")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate expectedRentalOn;

    @NotNull(message = "返却予定日は必須です")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate expectedReturnOn;

    @NotBlank(message = "在庫管理番号は必須です")
    private String stockId;

    @NotNull(message = "貸出ステータスは必須です")
    private Integer status;
}
