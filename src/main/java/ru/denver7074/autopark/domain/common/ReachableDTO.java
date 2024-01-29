package ru.denver7074.autopark.domain.common;


import ru.denver7074.autopark.service.CrudService;

public interface ReachableDTO extends GenericIdProjection<Long>{

    IdentityEntity reach(CrudService crudService);

    default void validate(CrudService crudService) {
    }

    default void reachTransient(CrudService crudService) {
    }
    
}
