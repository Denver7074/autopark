package ru.denver7074.autopark.domain.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ru.denver7074.autopark.service.CrudService;

import java.time.LocalDateTime;

@Data
@MappedSuperclass
@FieldDefaults(level = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public abstract class IdentityEntity implements ReachableDTO {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @JsonIgnore
    @CreatedDate
    LocalDateTime creatDate;
    @JsonIgnore
    @LastModifiedDate
    LocalDateTime lastModDate;
    Boolean archived = Boolean.FALSE;

    @Override
    public IdentityEntity reach(CrudService crudService) {
        validate(crudService);
        return this;
    }
}
