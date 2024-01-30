package ru.denver7074.autopark.mapper;

import ru.denver7074.autopark.domain.common.IdentityEntity;

import java.io.Serializable;

public interface Mapper<E extends IdentityEntity, DTO extends Serializable, D> {

    DTO toDTO(E e);

    E toEntity(D d);
}
