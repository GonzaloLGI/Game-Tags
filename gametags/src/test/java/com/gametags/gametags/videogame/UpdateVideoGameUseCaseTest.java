package com.gametags.gametags.videogame;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.gametags.gametags.application.videogame.UpdateVideoGameUseCase;
import com.gametags.gametags.domain.model.VideoGame;
import com.gametags.gametags.domain.services.VideoGameService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UpdateVideoGameUseCaseTest {

  @InjectMocks
  UpdateVideoGameUseCase useCase;

  @Mock
  VideoGameService service;

  @Test
  public void updateClassification() {
    //GIVEN
    VideoGame oldVideogame = VideoGame.builder()
        .id(UUID.randomUUID())
        .name("name1")
        .developer("developer1")
        .platforms(List.of("platform11", "platform12"))
        .uploadDateTime(LocalDateTime.now())
        .build();
    VideoGame newVideogame = VideoGame.builder()
        .id(UUID.randomUUID())
        .name("name2")
        .developer("developer2")
        .platforms(List.of("platform21", "platform22"))
        .uploadDateTime(LocalDateTime.now())
        .build();
    when(service.updateVideoGame(newVideogame)).thenReturn(newVideogame);
    when(service.findOneVideoGame(any(UUID.class))).thenReturn(oldVideogame);

    //WHEN
    VideoGame returnedVideogame = useCase.updateVideoGame(newVideogame);

    //THEN
    assertNotEquals(oldVideogame, returnedVideogame);
    assertEquals(newVideogame, returnedVideogame);

  }
}