package ru.denver7074.autopark.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
public class Seller extends IdentityEntity {

    String name;
    String email;
    String fio;
    @OneToMany(mappedBy = "seller")
    List<Owner> owners = new ArrayList<>();

}
