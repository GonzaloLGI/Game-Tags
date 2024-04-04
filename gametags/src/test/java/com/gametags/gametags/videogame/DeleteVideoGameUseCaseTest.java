package com.gametags.gametags.videogame;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import com.gametags.gametags.application.videogame.DeleteVideoGameUseCase;
import com.gametags.gametags.domain.model.VideoGame;
import com.gametags.gametags.domain.services.VideoGameService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DeleteVideoGameUseCaseTest {

  @InjectMocks
  DeleteVideoGameUseCase useCase;

  @Mock
  VideoGameService service;

  @Test
  public void canDelete() {
    //GIVEN
    VideoGame videogame = VideoGame.builder()
        .id(UUID.randomUUID())
        .name("name")
        .developer("developer")
        .platforms(List.of("platform1", "platform2"))
        .uploadDateTime(LocalDateTime.now())
        .build();
    when(service.findOneVideoGame(videogame.getId())).thenReturn(videogame);
    when(service.deleteVideoGame(videogame.getId())).thenReturn(videogame);

    //WHEN
    VideoGame result = useCase.deleteVideoGame(videogame.getId());

    //THEN
    assertEquals(videogame, result);
  }

  @Test
  public void cantDeleteBecauseVideoGameDoesntExist() {
    //GIVEN
    when(service.findOneVideoGame(any())).thenReturn(VideoGame.builder().build());

    //WHEN
    Exception exception = assertThrows(NoSuchElementException.class, () -> useCase.deleteVideoGame(UUID.randomUUID()));

    //THEN
    assertEquals("The videogame wanted to delete is not registered",exception.getMessage());

  }
}