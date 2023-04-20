package com.gametags.gametags.classification;

import com.gametags.application.classification.FindAllClassificationsUseCase;
import com.gametags.domain.Classification;
import com.gametags.domain.ClassificationService;
import com.gametags.infrastructure.ClassificationDAO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FindAllClassificationUseCaseTest {

    @InjectMocks
    FindAllClassificationsUseCase useCase;

    @Mock
    ClassificationService service;

    @Test
    public void findAll(){
        //GIVEN
        Classification classification1 = Classification.builder()
                .id(UUID.randomUUID())
                .tag("tag1")
                .url("url1")
                .country("country1")
                .system("system1")
                .build();
        Classification classification2 = Classification.builder()
                .id(UUID.randomUUID())
                .tag("tag2")
                .url("url2")
                .country("country2")
                .system("system2")
                .build();
        List<Classification> list = List.of(classification1,classification2);
        when(useCase.findAllClassifications()).thenReturn(list);

        //WHEN
        List<Classification> result = useCase.findAllClassifications();

        //THEN
        assertEquals(list.size(),result.size());
        assertEquals(list.get(0).getId(),result.get(0).getId());
    }
}
