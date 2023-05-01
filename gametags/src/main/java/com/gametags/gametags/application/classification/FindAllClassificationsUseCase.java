package com.gametags.gametags.application.classification;

import com.gametags.gametags.domain.Classification;
import com.gametags.gametags.domain.ClassificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class FindAllClassificationsUseCase {
    @Autowired
    private ClassificationService service;
    public List<Classification> findAllClassifications() {
        return service.findAllClassifications();
    }
}