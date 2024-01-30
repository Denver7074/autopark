package ru.denver7074.autopark.api.dto.response;


import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.springframework.util.CollectionUtils;
import ru.denver7074.autopark.domain.Car;
import ru.denver7074.autopark.domain.Owner;
import ru.denver7074.autopark.domain.Seller;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OwnerResponse implements Serializable {

    String fullNameOwner;
    String numberPhone;
    String email;
    List<CarResponse> carResponses = new ArrayList<>();

    @Data
    @Accessors(chain = true)
    @FieldDefaults(level = AccessLevel.PRIVATE)
    static class CarResponse {
        String vin;
        LocalDate buildDate;
        SellerResponse sellerResponse;

        public static CarResponse fromCarEntity(Car car) {
            SellerResponse selResp = SellerResponse.toDto(car.getSeller());

            return new CarResponse()
                    .setVin(car.getVin())
                    .setBuildDate(car.getBuildDate())
                    .setSellerResponse(selResp);
        }
    }

    @Data
    @Accessors(chain = true)
    @FieldDefaults(level = AccessLevel.PRIVATE)
    static class SellerResponse {
        String nameCompany;
        String emailCompany;
        String fullNameWorker;

        public static SellerResponse toDto(Seller seller) {
            return new SellerResponse()
                    .setNameCompany(seller.getNameCompany())
                    .setEmailCompany(seller.getEmailCompany())
                    .setFullNameWorker(seller.getFullNameWorker());
        }

    }

    public static OwnerResponse toDto(Owner owner) {
        List<CarResponse> carResponses = owner.getCars().stream()
                .map(CarResponse::fromCarEntity)
                .collect(Collectors.toList());

        return new OwnerResponse()
                .setFullNameOwner(owner.getFullNameOwner())
                .setNumberPhone(owner.getNumberPhone())
                .setEmail(owner.getEmail())
                .setCarResponses(carResponses.isEmpty() ? Collections.emptyList() : carResponses);
    }
}
