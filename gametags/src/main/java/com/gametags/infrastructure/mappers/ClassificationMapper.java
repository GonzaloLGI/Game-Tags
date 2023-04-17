package com.gametags.infrastructure.mappers;

import com.gametags.infrastructure.ClassificationDAO;
import com.gametags.infrastructure.ClassificationDTO;

import java.util.UUID;

public class ClassificationMapper {
    public ClassificationDAO toDomain(ClassificationDTO dto){
        return ClassificationDAO.builder()
                .id(UUID.randomUUID())
                .system(dto.getSystem())
                .country(dto.getCountry())
                .tag(dto.getTag())
                .url(dto.getUrl())
                .build();
    }

    public ClassificationDTO toDto(ClassificationDAO dao){
        return ClassificationDTO.builder()
                .system(dao.getSystem())
                .country(dao.getCountry())
                .tag(dao.getTag())
                .url(dao.getUrl())
                .build();
    }
}
