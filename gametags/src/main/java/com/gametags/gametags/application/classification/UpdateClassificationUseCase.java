package com.gametags.gametags.application.classification;

import com.gametags.gametags.domain.Classification;
import com.gametags.gametags.domain.ClassificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.NoSuchElementException;
import java.util.Objects;

@Component
@Slf4j
public class UpdateClassificationUseCase {
    @Autowired
    private ClassificationService service;
    public Classification updateClassification(Classification classification) {
        Classification foundClassification = service.findOneClassification(classification.getId());
        if(Objects.isNull(foundClassification.getId())){
            throw new NoSuchElementException("The classification is not registered");
        }
        return service.updateClassification(classification);
    }
}
