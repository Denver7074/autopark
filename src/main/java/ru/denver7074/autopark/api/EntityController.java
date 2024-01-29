package ru.denver7074.autopark.api;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.denver7074.autopark.api.dto.request.CarRequest;
import ru.denver7074.autopark.api.dto.request.OwnerRequest;
import ru.denver7074.autopark.api.dto.request.SellerRequest;
import ru.denver7074.autopark.api.response.PositiveResponse;
import ru.denver7074.autopark.api.response.ResponseApi;
import ru.denver7074.autopark.domain.Car;
import ru.denver7074.autopark.domain.Owner;
import ru.denver7074.autopark.domain.Seller;
import ru.denver7074.autopark.service.CrudService;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class EntityController {

    CrudService<Long> crudService;

    @RestController
    @RequestMapping("car")
    class CarController extends AbstractController<Car, CarRequest> {

        public CarController(CrudService<Long> crudService) {
            super(Car.class, CarRequest.class, crudService);
        }

        @PostMapping("{ownerId}/add")
        @Operation(description = "Метод создания машины",
                summary = "Метод создания машины")
        public PositiveResponse<Car> createSeller(@RequestBody CarRequest request, @PathVariable Long ownerId) {
            Owner owner = crudService.findById(ownerId, Owner.class);
            Car car = new Car()
                    .setVin(request.getVin())
                    .setBuildDate(request.getBuildDate())
                    .setOwner(owner);
            return ResponseApi.positiveResponse(crudService.create(car));
        }

        @PutMapping("{carId}/change/{ownerId}")
        @Operation(description = "Метод сменны владельца машины",
                summary = "Метод сменны владельца машины")
        public PositiveResponse<Car> createSeller(@PathVariable @NotBlank Long carId, @PathVariable @NotBlank Long ownerId) {
            Car car = crudService.findById(carId, Car.class);
            Owner owner = crudService.findById(ownerId, Owner.class);
            car.setOwner(owner);
            return ResponseApi.positiveResponse(crudService.update(car));
        }

    }

    @RestController
    @RequestMapping("owner")
    class OwnerController extends AbstractController<Owner, OwnerRequest> {

        public OwnerController(CrudService<Long> crudService) {
            super(Owner.class, OwnerRequest.class, crudService);
        }

        @PostMapping("{sellerId}/add")
        @Operation(description = "Метод создания владельца машин",
                summary = "Метод создания владельца машин")
        public PositiveResponse<Owner> createSeller(@RequestBody @Validated OwnerRequest request, @PathVariable Long sellerId) {
            Seller seller = crudService.findById(sellerId, Seller.class);
            Owner owner = new Owner()
                    .setFullNameOwner(request.getFullNameOwner())
                    .setEmail(request.getEmail())
                    .setNumberPhone(request.getNumberPhone())
                    .setSeller(seller);
            return ResponseApi.positiveResponse(crudService.create(owner));
        }

        @PutMapping("{ownerId}/change/{sellerId}")
        @Operation(description = "Метод сменны диллера",
                summary = "Метод сменны диллера")
        public PositiveResponse<Owner> createSeller(@PathVariable Long ownerId, @PathVariable Long sellerId) {
            Seller seller = crudService.findById(sellerId, Seller.class);
            Owner owner = crudService.findById(ownerId, Owner.class);
            owner.setSeller(seller);
            return ResponseApi.positiveResponse(crudService.update(owner));
        }

    }

    @RestController
    @RequestMapping("seller")
    class SellerController extends AbstractController<Seller, SellerRequest>{

        public SellerController(CrudService<Long> crudService) {
            super(Seller.class, SellerRequest.class, crudService);
        }

        @PostMapping("/add")
        @Operation(description = "Метод создания диллера",
                summary = "Метод создания диллера")
        public PositiveResponse<Seller> createSeller(@RequestBody @Validated SellerRequest request) {
            Seller seller = new Seller()
                    .setEmailCompany(request.getEmailCompany())
                    .setNameCompany(request.getNameCompany())
                    .setFullNameWorker(request.getFullNameWorker());
            return ResponseApi.positiveResponse(crudService.create(seller));
        }
    }

    @RestController
    @RequestMapping("velocity")
    class WayController {

        @PostMapping(name = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        @Operation(description = "Метод определения пройденного пути по координатам",
                summary = "Метод определения пройденного пути по координатам")
        public PositiveResponse<Integer> calculateWay(@RequestPart(name = "file") MultipartFile file) throws IOException {
            return ResponseApi.positiveResponse(file.getBytes().length);
        }
    }
}
