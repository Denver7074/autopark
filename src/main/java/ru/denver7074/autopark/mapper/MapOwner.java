package ru.denver7074.autopark.mapper;

import org.springframework.stereotype.Component;
import ru.denver7074.autopark.api.dto.request.OwnerRequest;
import ru.denver7074.autopark.api.dto.response.OwnerResponse;
import ru.denver7074.autopark.domain.Owner;
import ru.denver7074.autopark.domain.common.IdentityEntity;

@Component
public class MapOwner implements Mapper<Owner, OwnerResponse, OwnerRequest> {

    @Override
    public OwnerResponse toDTO(Owner owner) {
        return OwnerResponse.toDto(owner);
    }

    @Override
    public Owner toEntity(OwnerRequest request) {
        return new Owner()
                .setFullNameOwner(request.getFullNameOwner())
                .setEmail(request.getEmail())
                .setNumberPhone(request.getNumberPhone());
    }


}
