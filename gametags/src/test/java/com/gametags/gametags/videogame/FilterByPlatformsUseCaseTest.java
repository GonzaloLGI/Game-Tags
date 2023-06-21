package com.gametags.gametags.videogame;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.gametags.gametags.application.videogame.filter_videogames.FilterByDeveloperUseCase;
import com.gametags.gametags.application.videogame.filter_videogames.FilterByPlatformsUseCase;
import com.gametags.gametags.domain.model.VideoGame;
import com.gametags.gametags.domain.services.VideoGameService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class FilterByPlatformsUseCaseTest {

  @InjectMocks
  FilterByPlatformsUseCase useCase;

  @Mock
  VideoGameService service;

  @Test
  public void filterByPlatforms() {
    //GIVEN
    VideoGame videogame1 = VideoGame.builder()
        .id(UUID.randomUUID())
        .name("name1")
        .developer("Rockstar")
        .platforms(List.of("PS2", "XBOX"))
        .uploadDateTime(LocalDateTime.now())
        .build();
    VideoGame videogame2 = VideoGame.builder()
        .id(UUID.randomUUID())
        .name("name1")
        .developer("Rockstar")
        .platforms(List.of("PS2", "XBOX"))
        .uploadDateTime(LocalDateTime.now())
        .build();
    List<VideoGame> list = List.of(videogame1,videogame2);
    when(service.findAllVideoGamesByPlatforms(any())).thenReturn(list);

    //WHEN
    List<VideoGame> result = useCase.videoGamesByPlatforms(List.of("PS2","XBOX"));

    //THEN
    assertEquals(null, 2, result.size());
    assertEquals(null, true, result.get(0).getPlatforms().containsAll(List.of("PS2","XBOX")));

  }
}
