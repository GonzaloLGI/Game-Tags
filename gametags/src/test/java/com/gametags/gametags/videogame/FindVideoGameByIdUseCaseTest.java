package com.gametags.gametags.videogame;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import com.gametags.gametags.application.videogame.FindVideoGameByIdUseCase;
import com.gametags.gametags.domain.model.VideoGame;
import com.gametags.gametags.domain.services.VideoGameService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FindVideoGameByIdUseCaseTest {

  @InjectMocks
  FindVideoGameByIdUseCase useCase;

  @Mock
  VideoGameService service;

  @Test
  public void findById() {
    //GIVEN
    VideoGame videogame = VideoGame.builder()
        .id(UUID.randomUUID())
        .name("name")
        .developer("developer")
        .platforms(List.of("platform1", "platform2"))
        .uploadDateTime(LocalDateTime.now())
        .build();
    when(service.findOneVideoGame(any())).thenReturn(videogame);

    //WHEN
    VideoGame result = useCase.findOneVideoGame(UUID.randomUUID());

    //THEN
    assertEquals(result, videogame);

  }

  @Test
  public void cantFindClassificationBecauseDoesntExist() {
    //GIVEN
    when(service.findOneVideoGame(any())).thenReturn(null);

    //WHEN
    assertThrows(NoSuchElementException.class, () -> useCase.findOneVideoGame(UUID.randomUUID()));
  }
}