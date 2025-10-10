package org.iranshahi.zoochallenge.business.mapper;

import org.iranshahi.zoochallenge.business.dto.AnimalDto;
import org.iranshahi.zoochallenge.data.model.Animal;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class AnimalMapper {
    public Animal toEntity(AnimalDto animalDto) {
        return Animal.builder()
                .title(animalDto.title())
                .roomId(animalDto.roomId())
                .located(animalDto.located())
                .build();
    }

    public AnimalDto toDto(Animal animal) {
        return new AnimalDto(animal.getId(),
                animal.getTitle(),
                animal.getLocated(),
                animal.getRoomId(),
                animal.getFavouriteRoomIds());
    }

    public Page<AnimalDto> toDtoPage(Page<Animal> animals) {
        return animals.map(this::toDto);
    }

}
