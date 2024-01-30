package ru.denver7074.autopark.mapper;

import org.springframework.stereotype.Component;
import ru.denver7074.autopark.api.dto.request.SellerRequest;
import ru.denver7074.autopark.api.dto.response.SellerResponse;
import ru.denver7074.autopark.domain.Seller;

@Component
public class MapSeller implements Mapper<Seller, SellerResponse, SellerRequest> {

    @Override
    public SellerResponse toDTO(Seller seller) {
        return SellerResponse.toDto(seller);
    }

    @Override
    public Seller toEntity(SellerRequest request) {
        return new Seller()
                .setEmailCompany(request.getEmailCompany())
                .setNameCompany(request.getNameCompany())
                .setFullNameWorker(request.getFullNameWorker());
    }
}
