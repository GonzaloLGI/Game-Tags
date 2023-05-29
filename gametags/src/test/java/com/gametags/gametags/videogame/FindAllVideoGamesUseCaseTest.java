package com.gametags.gametags.videogame;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.gametags.gametags.application.videogame.FindAllVideoGamesUseCase;
import com.gametags.gametags.domain.model.VideoGame;
import com.gametags.gametags.domain.services.VideoGameService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FindAllVideoGamesUseCaseTest {

  @InjectMocks
  FindAllVideoGamesUseCase useCase;

  @Mock
  VideoGameService service;

  @Test
  public void findAll() {
    //GIVEN
    VideoGame videogame1 = VideoGame.builder()
        .id(UUID.randomUUID())
        .name("name1")
        .developer("developer1")
        .platforms(List.of("platform11", "platform12"))
        .uploadDateTime(LocalDateTime.now())
        .build();
    VideoGame videogame2 = VideoGame.builder()
        .id(UUID.randomUUID())
        .name("name2")
        .developer("developer2")
        .platforms(List.of("platform21", "platform22"))
        .uploadDateTime(LocalDateTime.now())
        .build();
    List<VideoGame> list = List.of(videogame1, videogame2);
    when(useCase.findAllVideoGames()).thenReturn(list);

    //WHEN
    List<VideoGame> result = useCase.findAllVideoGames();

    //THEN
    assertEquals(list.size(), result.size());
    assertEquals(list.get(0).getId(), result.get(0).getId());
  }
}