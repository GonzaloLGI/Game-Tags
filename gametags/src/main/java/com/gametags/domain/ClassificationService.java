package com.gametags.domain;

import com.gametags.infrastructure.adapters.ClassificationAdapter;
import com.gametags.infrastructure.ClassificationDAO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Slf4j
@Builder
public class ClassificationService {
    private ClassificationAdapter adapter;

    public Classification createClassification(Classification classification) {
        return adapter.create(classification);
    }

    public Classification findOneClassification(UUID id) {
        return adapter.findById(id);
    }

    public List<Classification> findAllClassifications() {
        return adapter.findAll();
    }

    public Classification updateClassification(Classification classification) {
        return adapter.update(classification);
    }

    public Classification deleteClassification(UUID id) {
        return adapter.delete(id);
    }
}
