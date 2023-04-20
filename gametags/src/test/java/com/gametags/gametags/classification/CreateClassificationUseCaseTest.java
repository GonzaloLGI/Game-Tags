package com.gametags.gametags.classification;

import com.gametags.application.classification.create_classification.CreateClassificationUseCase;
import com.gametags.domain.ClassificationService;
import com.gametags.infrastructure.ClassificationDAO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateClassificationUseCaseTest {

    @InjectMocks
    CreateClassificationUseCase useCase;

    @Mock
    ClassificationService service;

    @Test
    public void createClassification(){
        //GIVEN
        ClassificationDAO classification = ClassificationDAO.builder()
                .id(UUID.randomUUID())
                .tag("tag")
                .url("url")
                .country("country")
                .system("system")
                .build();
        when(service.createClassification(any(ClassificationDAO.class))).thenReturn(classification);

        //WHEN
        ClassificationDAO returnedClassification = useCase.createClassification(classification);

        //THEN
        assertEquals(classification,returnedClassification);

    }
}
