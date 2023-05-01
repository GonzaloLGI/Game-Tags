package com.gametags.gametags.infrastructure.mappers;

import com.gametags.gametags.application.classification.create_classification.CreateClassificationInput;
import com.gametags.gametags.domain.Classification;
import com.gametags.gametags.infrastructure.daos.ClassificationDAO;
import com.gametags.gametags.infrastructure.dtos.ClassificationDTO;
import lombok.Builder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Builder
public class ClassificationMapper {
    public ClassificationDAO toEntity(Classification domain){
        return ClassificationDAO.builder()
                .id(domain.getId())
                .system(domain.getSystem())
                .country(domain.getCountry())
                .tag(domain.getTag())
                .url(domain.getUrl())
                .build();
    }

    public Classification fromDtoToDomain(ClassificationDTO dto){
        return Classification.builder()
                .id(dto.getId())
                .system(dto.getSystem())
                .country(dto.getCountry())
                .tag(dto.getTag())
                .url(dto.getUrl())
                .build();
    }

    public Classification fromEntityToDomain(ClassificationDAO dao){
        return Classification.builder()
                .id(dao.getId())
                .system(dao.getSystem())
                .country(dao.getCountry())
                .tag(dao.getTag())
                .url(dao.getUrl())
                .build();
    }

    public ClassificationDTO toDto(Classification classification){
        return ClassificationDTO.builder()
                .id(classification.getId())
                .system(classification.getSystem())
                .country(classification.getCountry())
                .tag(classification.getTag())
                .url(classification.getUrl())
                .build();
    }

    public ClassificationDTO fromInputToDto(CreateClassificationInput input){
        return ClassificationDTO.builder()
                .id(UUID.randomUUID())
                .system(input.getSystem())
                .country(input.getCountry())
                .tag(input.getTag())
                .url(input.getUrl())
                .build();
    }

    public ClassificationDTO fromCreateInputToDto(CreateClassificationInput input){
        return ClassificationDTO.builder()
                .id(input.getId())
                .system(input.getSystem())
                .country(input.getCountry())
                .tag(input.getTag())
                .url(input.getUrl())
                .build();
    }

}
