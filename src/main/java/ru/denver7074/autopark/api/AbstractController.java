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
import ru.denver7074.autopark.service.CrudService;

import java.util.List;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AbstractController<E extends IdentityEntity, D> {

    @Getter
    Class<E> clazz;
    @Getter
    Class<D> dtoClazz;
    CrudService<Long> crudService;


    public AbstractController(Class<E> clazz, Class<D> dtoClazz, CrudService<Long> crudService) {
        this.clazz = clazz;
        this.crudService = crudService;
        this.dtoClazz = dtoClazz;
    }

    @DeleteMapping("{id}")
    @Operation(description = "Метод удаления элемента справочника по идентификатору",
            summary = "Метод удаления элемента справочника по идентификатору")
    public PositiveResponse<String> deleteById(@PathVariable @NotBlank Long id) {
        crudService.delete(id, clazz);
        return ResponseApi.positiveResponse("Ok");
    }

    @GetMapping("find/{id}")
    @Operation(description = "Метод поиска по id",
            summary = "Метод поиска по id")
    public PositiveResponse<E> findById(@PathVariable @NotBlank Long id) {
        return ResponseApi.positiveResponse(crudService.findById(id, clazz));
    }

    @GetMapping("findAll")
    @Operation(description = "Метод поиска сущностей",
            summary = "Метод поиска сущностей")
    public PositiveResponse<List<E>> findAll() {
        return ResponseApi.positiveResponse(crudService.findAll(clazz));
    }

    @PatchMapping("update/{id}")
    @Operation(description = "Метод поиска сущностей",
            summary = "Метод поиска сущностей")
    public PositiveResponse<E> update(@PathVariable Long id, @RequestBody D dto) {
        return ResponseApi.positiveResponse(crudService.update(dto, id, clazz));
    }
}
