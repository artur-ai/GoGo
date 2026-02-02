package com.maiboroda.GoGo.mapper;

import com.maiboroda.GoGo.dto.CarRequestDto;
import com.maiboroda.GoGo.dto.CarResponseDto;
import com.maiboroda.GoGo.entity.Car;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CarMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "countries", ignore = true)
    Car toEntity(CarRequestDto carRequestDto);

    @Mapping(target = "countries", expression = "java(mapCountries(car.getCountries()))")
    CarResponseDto toResponseDto(Car car);

    List<CarResponseDto> toResponseDtoList(List<Car> cars);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateCarFromDto(CarRequestDto carRequestDto, @MappingTarget Car car);

    default List<String> mapCountries(List<com.maiboroda.GoGo.entity.Country> countries) {
        if (countries == null) {
            return null;
        }
        return countries.stream()
                .map(com.maiboroda.GoGo.entity.Country::getName)
                .toList();
    }
}