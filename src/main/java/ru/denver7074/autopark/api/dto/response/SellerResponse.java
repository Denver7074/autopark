package ru.denver7074.autopark.api.dto.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.ObjectUtils;
import ru.denver7074.autopark.domain.Car;
import ru.denver7074.autopark.domain.Owner;
import ru.denver7074.autopark.domain.Seller;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.apache.commons.collections4.CollectionUtils.isEmpty;


@Data
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SellerResponse implements Serializable {

    String nameCompany;
    String emailCompany;
    String fullNameWorker;
    List<CarResponse> carResponses = new ArrayList<>();

    @Data
    @Accessors(chain = true)
    @FieldDefaults(level = AccessLevel.PRIVATE)
    static class CarResponse {
        String vin;
        LocalDate buildDate;
        OwnerResponse ownerResponse;

        public static CarResponse fromCarEntity(Car car) {
            OwnerResponse ownResp = null;
            if (ObjectUtils.isNotEmpty(car.getOwner())) {
                ownResp = OwnerResponse.toDto(car.getOwner());
            }

            return new CarResponse()
                    .setVin(car.getVin())
                    .setBuildDate(car.getBuildDate())
                    .setOwnerResponse(ownResp);
        }
    }

    @Data
    @Accessors(chain = true)
    @FieldDefaults(level = AccessLevel.PRIVATE)
    static class OwnerResponse {
        String fullNameOwner;
        String numberPhone;
        String email;

        public static OwnerResponse toDto(Owner owner) {
            return new OwnerResponse()
                    .setFullNameOwner(owner.getFullNameOwner())
                    .setNumberPhone(owner.getNumberPhone())
                    .setEmail(owner.getEmail());
        }
    }

    public static SellerResponse toDto(Seller seller) {
        List<CarResponse> carResps = seller.getCars()
                .stream()
                .map(CarResponse::fromCarEntity)
                .toList();

        return new SellerResponse()
                .setNameCompany(seller.getNameCompany())
                .setEmailCompany(seller.getEmailCompany())
                .setFullNameWorker(seller.getFullNameWorker())
                .setCarResponses(carResps);
    }

}
