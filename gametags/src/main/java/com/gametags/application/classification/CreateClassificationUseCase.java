package com.gametags.application.classification;

import com.gametags.domain.ClassificationService;
import com.gametags.infrastructure.ClassificationDAO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CreateClassificationUseCase {

    private ClassificationService service;

    public ClassificationDAO createClassification(ClassificationDAO dao) {
        return service.createClassification(dao);
    }
}
