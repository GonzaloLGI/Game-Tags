package com.gametags.application.classification;

import com.gametags.domain.ClassificationService;
import com.gametags.infrastructure.ClassificationDAO;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class FindAllClassificationsUseCase {
    private ClassificationService service;
    public List<ClassificationDAO> findAllClassifications() {
        return service.findAllClassifications();
    }
}
