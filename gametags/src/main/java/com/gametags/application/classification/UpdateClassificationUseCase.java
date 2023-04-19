package com.gametags.application.classification;

import com.gametags.domain.ClassificationService;
import com.gametags.infrastructure.ClassificationDAO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UpdateClassificationUseCase {
    private ClassificationService service;
    public ClassificationDAO updateClassification(ClassificationDAO dao) {
        return service.updateClassification(dao);
    }
}
