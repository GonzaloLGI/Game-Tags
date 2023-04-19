package com.gametags.domain;

import com.gametags.infrastructure.adapters.ClassificationAdapter;
import com.gametags.infrastructure.ClassificationDAO;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Builder
public class ClassificationService {
    private ClassificationAdapter adapter;

    public ClassificationDAO createClassification(ClassificationDAO dao) {
        return adapter.create(dao);
    }

    public ClassificationDAO findOneClassification(UUID id) {
        return adapter.findById(id);
    }

    public List<ClassificationDAO> findAllClassifications() {
        return adapter.findAll();
    }

    public ClassificationDAO updateClassification(ClassificationDAO dao) {
        return adapter.update(dao);
    }

    public ClassificationDAO deleteClassification(UUID id) {
        return adapter.delete(id);
    }
}
