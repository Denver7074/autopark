package ru.denver7074.autopark.api;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import ru.denver7074.autopark.api.response.PositiveResponse;
import ru.denver7074.autopark.api.response.ResponseApi;
import ru.denver7074.autopark.domain.Seller;
import ru.denver7074.autopark.domain.common.IdentityEntity;
import ru.denver7074.autopark.mapper.Mapper;
import ru.denver7074.autopark.service.CrudService;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AbstractController<E extends IdentityEntity, D, DTO extends Serializable> {

    @Getter
    Class<E> clazz;
    @Getter
    Class<D> dtoRequest;
    @Getter
    Class<DTO> dtoResponse;
    CrudService<Long> crudService;
    Mapper<E, DTO, D> mapper;

    public AbstractController(Class<E> clazz, Class<D> dtoRequest, Class<DTO> dtoResponse,  CrudService<Long> crudService, Mapper<E, DTO, D> mapper) {
        this.clazz = clazz;
        this.dtoRequest = dtoRequest;
        this.dtoResponse = dtoResponse;
        this.crudService = crudService;
        this.mapper = mapper;
    }

    @DeleteMapping("{id}")
    @Operation(description = "Метод удаления элемента справочника по идентификатору",
            summary = "Метод удаления элемента справочника по идентификатору")
    public PositiveResponse<String> deleteById(@PathVariable Long id) {
        crudService.delete(id, clazz);
        return ResponseApi.positiveResponse("Ok");
    }

    @GetMapping("find/{id}")
    @Operation(description = "Метод поиска по id",
            summary = "Метод поиска по id")
    public PositiveResponse<DTO> findById(@PathVariable Long id) {
        E byId = crudService.findById(id, clazz);
        return ResponseApi.positiveResponse(mapper.toDTO(byId));
    }

    @GetMapping("findAll")
    @Operation(description = "Метод поиска сущностей",
            summary = "Метод поиска сущностей")
    public PositiveResponse<List<DTO>> findAll() {
        List<DTO> dtos = crudService.findAll(clazz)
                .stream()
                .map(mapper::toDTO)
                .toList();
        return ResponseApi.positiveResponse(dtos);
    }

    @PatchMapping("update/{id}")
    @Operation(description = "Метод поиска сущностей",
            summary = "Метод поиска сущностей")
    public PositiveResponse<E> update(@PathVariable Long id, @RequestBody D dto) {
        E update = crudService.update(dto, id, clazz);
        return ResponseApi.positiveResponse(update);
    }
}
