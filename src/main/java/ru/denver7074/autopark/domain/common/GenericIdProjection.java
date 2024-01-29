package ru.denver7074.autopark.domain.common;

import java.io.Serializable;

public interface GenericIdProjection<ID> extends Serializable {

    ID getId();
}
