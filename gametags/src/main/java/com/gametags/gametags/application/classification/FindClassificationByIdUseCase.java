package com.gametags.gametags.application.classification;

import com.gametags.gametags.domain.Classification;
import com.gametags.gametags.domain.ClassificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;

@Component
@Slf4j
public class FindClassificationByIdUseCase {
    @Autowired
    private ClassificationService service;
    public Classification findOneClassification(UUID id) {
        Classification classification = service.findOneClassification(id);
        if(Objects.isNull(classification.getId())){
            throw new NoSuchElementException("The classification is not registered");
        }
        return classification;
    }
}
