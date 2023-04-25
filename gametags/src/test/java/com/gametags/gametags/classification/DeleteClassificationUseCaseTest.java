package com.gametags.gametags.classification;

import com.gametags.gametags.application.classification.DeleteClassificationUseCase;
import com.gametags.gametags.domain.Classification;
import com.gametags.gametags.domain.ClassificationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DeleteClassificationUseCaseTest {
    @InjectMocks
    DeleteClassificationUseCase useCase;

    @Mock
    ClassificationService service;

    @Test
    public void canDelete(){
        //GIVEN
        Classification classification = Classification.builder()
                .id(UUID.randomUUID())
                .tag("tag1")
                .url("url1")
                .country("country1")
                .system("system1")
                .build();
        when(service.findOneClassification(classification.getId())).thenReturn(classification);
        when(service.deleteClassification(classification.getId())).thenReturn(classification);

        //WHEN
        Classification result = useCase.deleteClassification(classification.getId());

        //THEN
        assertEquals(classification,result);
    }

    @Test
    public void cantDeleteBecauseClassificationDoesntExist(){
        //GIVEN
        when(service.findOneClassification(any())).thenReturn(null);

        //WHEN
        assertThrows(NoSuchElementException.class, () -> useCase.deleteClassification(UUID.randomUUID()));

        //THEN



    }
}
