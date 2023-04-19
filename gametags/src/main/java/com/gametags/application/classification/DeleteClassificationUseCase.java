package com.gametags.application.classification;

import com.gametags.domain.ClassificationService;
import com.gametags.infrastructure.ClassificationDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import java.util.NoSuchElementException;
import java.util.UUID;

@Slf4j
public class DeleteClassificationUseCase {
    private ClassificationService service;
    public ClassificationDAO deleteClassification(UUID id) {
        ClassificationDAO dao = service.findOneClassification(id);
        if(!ObjectUtils.isEmpty(dao)){
            return service.deleteClassification(id);
        }else{
            throw new NoSuchElementException("Non existing classification");
        }

    }
}
