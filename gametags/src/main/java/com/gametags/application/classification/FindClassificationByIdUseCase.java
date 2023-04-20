package com.gametags.application.classification;

import com.gametags.domain.ClassificationService;
import com.gametags.infrastructure.ClassificationDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import java.util.NoSuchElementException;
import java.util.UUID;

@Slf4j
public class FindClassificationByIdUseCase {
    @Autowired
    private ClassificationService service;
    public ClassificationDAO findOneClassification(UUID id) {
        ClassificationDAO dao = service.findOneClassification(id);
        if(ObjectUtils.isEmpty(dao)){
            throw new NoSuchElementException("The classification is not registered");
        }
        return dao;
    }
}
