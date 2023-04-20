package com.gametags.infrastructure.mappers;

import com.gametags.application.classification.create_classification.CreateClassificationInput;
import com.gametags.infrastructure.ClassificationDAO;
import com.gametags.infrastructure.ClassificationDTO;
import org.springframework.util.ObjectUtils;

import java.util.UUID;

public class ClassificationMapper {
    public ClassificationDAO toDomain(ClassificationDTO dto){
        return ClassificationDAO.builder()
                .id(dto.getId())
                .system(dto.getSystem())
                .country(dto.getCountry())
                .tag(dto.getTag())
                .url(dto.getUrl())
                .build();
    }

    public ClassificationDTO toDto(ClassificationDAO dao){
        return ClassificationDTO.builder()
                .id(dao.getId())
                .system(dao.getSystem())
                .country(dao.getCountry())
                .tag(dao.getTag())
                .url(dao.getUrl())
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
}
