package ru.denver7074.autopark.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import lombok.experimental.FieldNameConstants;
import ru.denver7074.autopark.domain.common.IdentityEntity;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@FieldNameConstants
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Owner extends IdentityEntity {

    String fioOwner;
    String number;
    String email;
    @OneToMany(mappedBy = "owner")
    List<Car> cars = new ArrayList<>();
    @ManyToOne
    Seller seller;
}
