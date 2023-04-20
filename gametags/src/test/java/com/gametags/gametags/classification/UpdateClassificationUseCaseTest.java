package com.gametags.gametags.classification;

import com.gametags.application.classification.UpdateClassificationUseCase;
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
public class UpdateClassificationUseCaseTest {
    @InjectMocks
    UpdateClassificationUseCase useCase;

    @Mock
    ClassificationService service;

    @Test
    public void updateClassification(){
        //GIVEN
        ClassificationDAO oldClassification = ClassificationDAO.builder()
                .id(UUID.randomUUID())
                .tag("tag")
                .url("url")
                .country("country")
                .system("system")
                .build();
        ClassificationDAO newClassification = ClassificationDAO.builder()
                .id(UUID.randomUUID())
                .tag("newTag")
                .url("newUrl")
                .country("newCountry")
                .system("newSystem")
                .build();
        when(service.updateClassification(newClassification)).thenReturn(newClassification);


        //WHEN
        ClassificationDAO returnedClassification = useCase.updateClassification(newClassification);

        //THEN
        assertEquals(newClassification,returnedClassification);

    }
}
