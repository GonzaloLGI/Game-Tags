package com.gametags.gametags.classification;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.NoSuchElementException;
import java.util.UUID;

import com.gametags.gametags.application.classification.create_classification.CreateClassificationUseCase;
import com.gametags.gametags.domain.model.Classification;
import com.gametags.gametags.domain.services.ClassificationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.config.annotation.AlreadyBuiltException;

@ExtendWith(MockitoExtension.class)
public class CreateClassificationUseCaseTest {

  @InjectMocks
  CreateClassificationUseCase useCase;

  @Mock
  ClassificationService service;

  @Test
  public void createClassification() {
    //GIVEN
    Classification classification = Classification.builder()
        .id(UUID.randomUUID())
        .tag("tag")
        .url("url")
        .country("country")
        .system("system")
        .build();
    when(service.createClassification(any(Classification.class))).thenReturn(classification);
    when(service.findOneClassification(any())).thenReturn(Classification.builder().build());
    when(service.findOneClassificationBySystemAndTag(any(), any())).thenReturn(Classification.builder().build());

    //WHEN
    Classification returnedClassification = useCase.createClassification(classification);

    //THEN
    assertEquals(classification, returnedClassification);

  }

  @Test
  public void cantCreateClassificationBecauseAlreadyExist() {
    //GIVEN
    Classification classification = Classification.builder()
            .id(UUID.randomUUID())
            .tag("tag")
            .url("url")
            .country("country")
            .system("system")
            .build();
    when(service.findOneClassification(any())).thenReturn(classification);

    //WHEN
    Exception exception = assertThrows(AlreadyBuiltException.class, () -> useCase.createClassification(classification));

    assertEquals("Classification already exists",exception.getMessage());
  }

}
