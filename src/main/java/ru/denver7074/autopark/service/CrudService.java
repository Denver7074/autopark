package ru.denver7074.autopark.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.CastUtils;
import org.springframework.stereotype.Service;
import ru.denver7074.autopark.domain.common.IdentityEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.apache.commons.lang3.BooleanUtils.isFalse;
import static ru.denver7074.autopark.utils.Errors.E001;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CrudService<ID> {

    ModelMapper modelMapper;
    EntityManager entityManager;

    public <E, D> E toMap(Class<E> clazz, D dto) {

        return modelMapper.map(dto, clazz);
    }

    public <E extends IdentityEntity> E create(E entity) {
        IdentityEntity merge = entityManager.merge(entity.reach(this));
        return CastUtils.cast(merge);
    }

    public <E extends IdentityEntity> E findById(ID id, Class<E> clazz) {
        return Optional.ofNullable(entityManager.find(clazz, id))
                .orElseThrow(() -> E001.thr(clazz.getSimpleName(), id));
    }

    public <E extends IdentityEntity, D> E update(D source, ID id, Class<E> clazz) {
        E target = findById(id, clazz);
        modelMapper.map(source, target.reach(this));
        entityManager.persist(target);
        return CastUtils.cast(target);
    }

    public <E extends IdentityEntity> E update(E entity) {
        entityManager.persist(entity);
        return entity;
    }

    public <E extends IdentityEntity>  void delete(ID id, Class<E> clazz) {
        E entity = findById(id, clazz);
        if(isFalse(entity.getArchived())){
            entity.setArchived(Boolean.TRUE);
            entityManager.persist(entity);
        } else update(entity);
    }

    public <E extends IdentityEntity> List<E> findAll(Class<E> clazz) {
        String syn = StringUtils.uncapitalize(clazz.getSimpleName().substring(0, 1));
        return entityManager.createQuery("select " + syn + " from "
                + clazz.getSimpleName() + " " + syn, clazz).getResultList();
    }

    public <E> List<E> find(Class<E> clazz, Map<String, Object> filter) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<E> query = cb.createQuery(clazz);
        Root<E> root = query.from(clazz);
        List<Predicate> predicates = new ArrayList<>();
        for (Map.Entry<String, Object> entry : filter.entrySet()) {
            String field = entry.getKey();
            Object value = entry.getValue();
            predicates.add(cb.equal(root.get(field), value));
        }

        query.where(predicates.toArray(new Predicate[0]));
        return entityManager.createQuery(query).getResultList();
    }
}
