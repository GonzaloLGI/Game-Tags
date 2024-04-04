package com.gametags.gametags.videogame;

import com.gametags.gametags.application.videogame.filter_videogames.FilterByDeveloperUseCase;
import com.gametags.gametags.application.videogame.filter_videogames.FilterBySystemUseCase;
import com.gametags.gametags.domain.model.Classification;
import com.gametags.gametags.domain.model.VideoGame;
import com.gametags.gametags.domain.services.VideoGameService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@ExtendWith(MockitoExtension.class)
public class FilterBySystemUseCaseTest {

  @InjectMocks
  FilterBySystemUseCase useCase;

  @Mock
  VideoGameService service;

  @Test
  public void filterByDeveloper() {
    //GIVEN
    VideoGame videogame1 = VideoGame.builder()
        .id(UUID.randomUUID())
        .name("name1")
        .developer("developer")
        .platforms(List.of("platform11", "platform12"))
            .classifications(List.of(Classification.builder().system("ESRB").build()))
        .uploadDateTime(LocalDateTime.now())
        .build();
    List<VideoGame> list = List.of(videogame1);
    when(service.findAllVideoGamesBySystem(any(String.class))).thenReturn(list);

    //WHEN
    List<VideoGame> result = useCase.videoGamesBySystem("ESRB");

    //THEN
    assertEquals(null, 1, result.size());
    assertEquals(null, 1, result.get(0).getClassifications().size());
    assertEquals(null, "ESRB", result.get(0).getClassifications().get(0).getSystem());

  }

}
