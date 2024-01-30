package ru.denver7074.autopark.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OwnerRequest implements Serializable {

    @NotBlank
    @Schema(description = "Полное имя владельца")
    String fullNameOwner;
    @Schema(description = "Номер телефона")
    String numberPhone;
    @Schema(description = "email")
    @NotBlank
    String email;

}
