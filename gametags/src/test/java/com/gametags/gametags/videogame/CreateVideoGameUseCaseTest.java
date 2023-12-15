package com.gametags.gametags.videogame;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.gametags.gametags.application.videogame.create_videogame.CreateVideoGameUseCase;
import com.gametags.gametags.domain.model.Classification;
import com.gametags.gametags.domain.model.VideoGame;
import com.gametags.gametags.domain.services.VideoGameService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CreateVideoGameUseCaseTest {

  @InjectMocks
  CreateVideoGameUseCase useCase;

  @Mock
  VideoGameService service;

  @Test
  public void createVideoGame() {
    //GIVEN
    VideoGame videogame = VideoGame.builder()
        .id(UUID.randomUUID())
        .name("name")
        .developer("developer")
        .platforms(List.of("platform1", "platform2"))
        .uploadDateTime(LocalDateTime.now())
        .build();
    VideoGame existingVideogame = VideoGame.builder()
            .id(UUID.randomUUID())
            .name("name")
            .developer("developer")
            .platforms(List.of("platform1", "platform2"))
            .uploadDateTime(LocalDateTime.now())
            .classifications(List.of(Classification.builder().build(), Classification.builder().build()))
            .build();
    when(service.createVideoGame(any(VideoGame.class))).thenReturn(videogame);
    when(service.findVideoGameByName(any())).thenReturn(existingVideogame);

    //WHEN
    VideoGame returnedClassification = useCase.createVideoGame(videogame);

    //THEN
    assertEquals(videogame.getId(), returnedClassification.getId());

  }
}