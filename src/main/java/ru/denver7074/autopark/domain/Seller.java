package ru.denver7074.autopark.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import lombok.experimental.FieldNameConstants;
import ru.denver7074.autopark.domain.common.IdentityEntity;
import ru.denver7074.autopark.service.CrudService;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.BooleanUtils.isFalse;
import static ru.denver7074.autopark.utils.Constants.pattern;
import static ru.denver7074.autopark.utils.Errors.E003;

@Data
@Entity
@FieldNameConstants
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Seller extends IdentityEntity {

    String nameCompany;
    String emailCompany;
    String fullNameWorker;
    @JsonIgnore
    @OneToMany(mappedBy = "seller")
    List<Car> cars = new ArrayList<>();

    public void validate(CrudService crudService) {
        E003.thr(isFalse(pattern.matcher(this.getEmailCompany()).matches()),
                this.getEmailCompany());
    }
}
