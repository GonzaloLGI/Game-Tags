package com.gametags.application.classification;

import com.gametags.domain.ClassificationService;
import com.gametags.infrastructure.ClassificationDAO;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class FindClassificationByIdUseCase {
    private ClassificationService service;
    public ClassificationDAO findOneClassification(UUID id) {
        return service.findOneClassification(id);
    }
}
