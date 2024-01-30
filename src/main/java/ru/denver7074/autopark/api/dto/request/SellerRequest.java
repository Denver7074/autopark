package ru.denver7074.autopark.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SellerRequest implements Serializable {

    @NotEmpty
    @Schema(description = "Название компании")
    String nameCompany;
    @NotBlank
    @Schema(description = "email компании")
    String emailCompany;
    @NotBlank
    @Schema(description = "Представитель компании")
    String fullNameWorker;

}
