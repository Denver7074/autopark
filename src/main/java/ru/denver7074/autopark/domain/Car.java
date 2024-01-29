package ru.denver7074.autopark.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import lombok.experimental.FieldNameConstants;
import ru.denver7074.autopark.domain.common.IdentityEntity;
import ru.denver7074.autopark.service.CrudService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;
import static ru.denver7074.autopark.utils.Errors.E003;
import static ru.denver7074.autopark.utils.Errors.E004;

@Data
@Entity
@FieldNameConstants
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Car extends IdentityEntity {

    String vin;
    LocalDate buildDate;
    @ManyToOne
    @JsonIgnoreProperties("car")
    @JoinColumn(name = "owner_id")
    Owner owner;

    public void validate(CrudService crudService) {
        E004.thr(isNotEmpty(crudService.find(Car.class, Map.of(Fields.vin, this.getVin()))), this.getVin());
    }

}
