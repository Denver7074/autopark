package ru.denver7074.autopark.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.denver7074.autopark.api.dto.request.CarRequest;
import ru.denver7074.autopark.api.dto.request.OwnerRequest;
import ru.denver7074.autopark.api.dto.request.SellerRequest;
import ru.denver7074.autopark.api.dto.response.OwnerResponse;
import ru.denver7074.autopark.api.dto.response.SellerResponse;
import ru.denver7074.autopark.api.response.PositiveResponse;
import ru.denver7074.autopark.api.response.ResponseApi;
import ru.denver7074.autopark.domain.Car;
import ru.denver7074.autopark.domain.Owner;
import ru.denver7074.autopark.domain.Seller;
import ru.denver7074.autopark.mapper.Mapper;
import ru.denver7074.autopark.service.CrudService;
import ru.denver7074.autopark.service.FileService;
import ru.denver7074.autopark.utils.Utils;

import java.util.Map;

import static ru.denver7074.autopark.utils.Errors.E005;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class EntityController {

    FileService fileService;
    CrudService<Long> crudService;

    @RestController
    @RequestMapping("car")
    @Tag(name = "Автомобиль", description = "Модуль работы с автомобилями")
    class CarController extends AbstractController<Car, CarRequest, Car> {

        private final Mapper<Car, Car, CarRequest> mapper;

        public CarController(CrudService<Long> crudService, Mapper<Car, Car, CarRequest> mapper) {
            super(Car.class, CarRequest.class, Car.class, crudService, mapper);
            this.mapper = mapper;
        }

        @PostMapping("{sellerId}/add")
        @Operation(description = "Метод создания машины",
                summary = "Метод создания машины",
                responses = {
                        @ApiResponse(responseCode = "002", description = "Отсутствуют обязательные поля"),
                        @ApiResponse(responseCode = "004", description = "Машина в vin номером уже сществует")
                }
        )
        public PositiveResponse<Car> createCar(@RequestBody CarRequest request, @PathVariable Long sellerId) {
            Seller seller = crudService.findById(sellerId, Seller.class);
            Car car = mapper.toEntity(request);
            car.setSeller(seller);
            return ResponseApi.positiveResponse(crudService.create(car));
        }

        @PutMapping("{carId}/buy/{ownerId}")
        @Operation(description = "Метод установления владельца машины",
                summary = "Метод установления владельца машины",
                responses = {
                        @ApiResponse(responseCode = "001", description = "Сущность с таким id не найдена")
                }
        )
        public PositiveResponse<Car> createOwner(@PathVariable Long carId, @PathVariable Long ownerId) {
            Car car = crudService.findById(carId, Car.class);
            Owner owner = crudService.findById(ownerId, Owner.class);
            car.setOwner(owner);
            return ResponseApi.positiveResponse(crudService.update(car));
        }

        @PutMapping("{carId}/change/{sellerId}")
        @Operation(description = "Метод смены диллера",
                summary = "Метод смены диллера",
                responses = {
                        @ApiResponse(responseCode = "001", description = "Сущность с таким id не найдена")
                }
        )
        public PositiveResponse<Car> createSeller(@PathVariable Long carId, @PathVariable Long sellerId) {
            Car car = crudService.findById(carId, Car.class);
            Seller seller = crudService.findById(sellerId, Seller.class);
            car.setSeller(seller);
            return ResponseApi.positiveResponse(crudService.update(car));
        }
    }

    @RestController
    @RequestMapping("owner")
    @Tag(name = "Владелец авто", description = "Модуль работы с владельцами авто")
    class OwnerController extends AbstractController<Owner, OwnerRequest, OwnerResponse> {

        private final Mapper<Owner, OwnerResponse, OwnerRequest> mapper;

        public OwnerController(CrudService<Long> crudService, Mapper<Owner, OwnerResponse, OwnerRequest> mapper) {
            super(Owner.class, OwnerRequest.class, OwnerResponse.class, crudService, mapper);
            this.mapper = mapper;
        }

        @PostMapping("/add")
        @Operation(description = "Метод создания владельца машин",
                summary = "Метод создания владельца машин",
                responses = {
                        @ApiResponse(responseCode = "002", description = "Отсутствуют обязательные поля"),
                        @ApiResponse(responseCode = "003", description = "Неккоректный ввод email")
                }
        )
        public PositiveResponse<OwnerResponse> createSeller(@RequestBody @Validated OwnerRequest request) {
            Owner entity = mapper.toEntity(request);
            Owner data = crudService.create(entity);
            return ResponseApi.positiveResponse(mapper.toDTO(data));
        }
    }

    @RestController
    @RequestMapping("seller")
    @Tag(name = "Диллер", description = "Модуль работы с диллером")
    class SellerController extends AbstractController<Seller, SellerRequest, SellerResponse> {

        private final Mapper<Seller, SellerResponse, SellerRequest> mapper;

        public SellerController(CrudService<Long> crudService, Mapper<Seller, SellerResponse, SellerRequest> mapper) {
            super(Seller.class, SellerRequest.class, SellerResponse.class, crudService, mapper);
            this.mapper = mapper;
        }

        @PostMapping("/add")
        @Operation(description = "Метод создания диллера",
                summary = "Метод создания диллера",
                responses = {
                        @ApiResponse(responseCode = "002", description = "Отсутствуют обязательные поля"),
                        @ApiResponse(responseCode = "003", description = "Неккоректный ввод email")
                }
        )
        public PositiveResponse<SellerResponse> createSeller(@RequestBody @Validated SellerRequest request) {
            Seller seller = mapper.toEntity(request);
            Seller data = crudService.create(seller);
            return ResponseApi.positiveResponse(mapper.toDTO(data));
        }
    }

    @RestController
    @RequestMapping("way")
    @Tag(name = "Определение пути", description = "Модуль определения пути по координатам")
    class WayController {

        @PostMapping(name = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        @Operation(description = "Метод определения пройденного пути по координатам",
                summary = "Метод определения пройденного пути по координатам")
        public PositiveResponse<Map<String, Double>> calculateWay(@RequestPart(name = "file", required = true) MultipartFile file) {
            E005.thr(file.getSize() == 0);
            return ResponseApi.positiveResponse(fileService.calculateWay(file));
        }
    }
}
