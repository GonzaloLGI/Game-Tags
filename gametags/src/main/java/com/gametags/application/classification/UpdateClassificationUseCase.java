package com.gametags.application.classification;

import com.gametags.domain.Classification;
import com.gametags.domain.ClassificationService;
import com.gametags.infrastructure.ClassificationDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class UpdateClassificationUseCase {
    @Autowired
    private ClassificationService service;
    public Classification updateClassification(Classification classification) {
        return service.updateClassification(classification);
    }
}
