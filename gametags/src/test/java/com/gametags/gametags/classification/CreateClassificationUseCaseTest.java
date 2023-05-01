package com.gametags.gametags.classification;

import com.gametags.gametags.application.classification.create_classification.CreateClassificationUseCase;
import com.gametags.gametags.domain.model.Classification;
import com.gametags.gametags.domain.services.ClassificationService;
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
        Classification classification = Classification.builder()
                .id(UUID.randomUUID())
                .tag("tag")
                .url("url")
                .country("country")
                .system("system")
                .build();
        when(service.createClassification(any(Classification.class))).thenReturn(classification);

        //WHEN
        Classification returnedClassification = useCase.createClassification(classification);

        //THEN
        assertEquals(classification,returnedClassification);

    }
}
