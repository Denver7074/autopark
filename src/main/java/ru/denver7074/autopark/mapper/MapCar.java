package ru.denver7074.autopark.mapper;

import org.springframework.stereotype.Component;
import ru.denver7074.autopark.api.dto.request.CarRequest;
import ru.denver7074.autopark.domain.Car;

@Component
public class MapCar implements Mapper<Car, Car, CarRequest>{


    @Override
    public Car toDTO(Car car) {
        return car;
    }

    @Override
    public Car toEntity(CarRequest request) {
        return new Car()
                .setVin(request.getVin())
                .setBuildDate(request.getBuildDate());
    }
}
