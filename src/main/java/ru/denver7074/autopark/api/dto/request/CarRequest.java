package ru.denver7074.autopark.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CarRequest implements Serializable {

    @NotBlank
    @Schema(description = "vin номер автомобиля")
    String vin;
    @NotBlank
    @Schema(description = "Дата выпуска")
    LocalDate buildDate;

}
