package com.gametags.gametags.videogame;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.gametags.gametags.application.videogame.filter_videogames.FilterByDeveloperUseCase;
import com.gametags.gametags.domain.model.VideoGame;
import com.gametags.gametags.domain.services.VideoGameService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class FilterByDeveloperUseCaseTest {

  @InjectMocks
  FilterByDeveloperUseCase useCase;

  @Mock
  VideoGameService service;

  @Test
  public void filterByDeveloper() {
    //GIVEN
    VideoGame videogame1 = VideoGame.builder()
        .id(UUID.randomUUID())
        .name("name1")
        .developer("Rockstar")
        .platforms(List.of("platform11", "platform12"))
        .uploadDateTime(LocalDateTime.now())
        .build();
    List<VideoGame> list = List.of(videogame1);
    when(service.findAllVideoGamesByDeveloper(any(String.class))).thenReturn(list);

    //WHEN
    List<VideoGame> result = useCase.videoGamesByDeveloper("Rockstar");

    //THEN
    assertEquals(null, 1, result.size());
    assertEquals(null, "Rockstar", result.get(0).getDeveloper());

  }

}
