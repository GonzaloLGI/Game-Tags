package com.gametags.application.classification;

import com.gametags.domain.ClassificationService;
import com.gametags.infrastructure.ClassificationDAO;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class DeleteClassificationUseCase {
    private ClassificationService service;
    public ClassificationDAO deleteClassification(UUID id) {
        return service.deleteClassification(id);
    }
}
