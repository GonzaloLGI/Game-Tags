package com.gametags.gametags.classification;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.NoSuchElementException;
import java.util.UUID;

import com.gametags.gametags.application.classification.UpdateClassificationUseCase;
import com.gametags.gametags.domain.model.Classification;
import com.gametags.gametags.domain.services.ClassificationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UpdateClassificationUseCaseTest {

  @InjectMocks
  UpdateClassificationUseCase useCase;

  @Mock
  ClassificationService service;

  @Test
  public void updateClassification() {
    //GIVEN
    Classification oldClassification = Classification.builder()
        .id(UUID.randomUUID())
        .tag("tag")
        .url("url")
        .country("country")
        .system("system")
        .build();
    Classification newClassification = Classification.builder()
        .id(UUID.randomUUID())
        .tag("newTag")
        .url("newUrl")
        .country("newCountry")
        .system("newSystem")
        .build();
    when(service.updateClassification(newClassification)).thenReturn(newClassification);
    when(service.findOneClassification(any(UUID.class))).thenReturn(oldClassification);

    //WHEN
    Classification returnedClassification = useCase.updateClassification(newClassification);

    //THEN
    assertNotEquals(oldClassification, returnedClassification);
    assertEquals(newClassification, returnedClassification);

  }

  @Test
  public void cantUpdateBecauseClassificationDoesntExist() {
    //GIVEN
    Classification newClassification = Classification.builder()
            .id(UUID.randomUUID())
            .tag("newTag")
            .url("newUrl")
            .country("newCountry")
            .system("newSystem")
            .build();
    when(service.findOneClassification(any())).thenReturn(Classification.builder().build());

    //WHEN
    Exception exception = assertThrows(NoSuchElementException.class, () -> useCase.updateClassification(newClassification));

    //THEN
    assertEquals("The classification is not registered",exception.getMessage());

  }

}
