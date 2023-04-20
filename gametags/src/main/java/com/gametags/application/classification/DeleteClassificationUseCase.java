package com.gametags.application.classification;

import com.gametags.domain.Classification;
import com.gametags.domain.ClassificationService;
import com.gametags.infrastructure.ClassificationDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import java.util.NoSuchElementException;
import java.util.UUID;

@Slf4j
public class DeleteClassificationUseCase {
    private ClassificationService service;
    public Classification deleteClassification(UUID id) {
        Classification classification = service.findOneClassification(id);
        if(!ObjectUtils.isEmpty(classification)){
            return service.deleteClassification(id);
        }else{
            throw new NoSuchElementException("Non existing classification");
        }

    }
}
