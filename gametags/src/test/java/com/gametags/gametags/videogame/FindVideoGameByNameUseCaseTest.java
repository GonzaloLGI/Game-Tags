package com.gametags.gametags.videogame;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import com.gametags.gametags.application.videogame.FindVideoGameByNameUseCase;
import com.gametags.gametags.domain.model.VideoGame;
import com.gametags.gametags.domain.services.VideoGameService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class FindVideoGameByNameUseCaseTest {

  @InjectMocks
  FindVideoGameByNameUseCase useCase;

  @Mock
  VideoGameService service;

  @Test
  public void findByName() {
    //GIVEN
    VideoGame videogame = VideoGame.builder()
        .id(UUID.randomUUID())
        .name("name")
        .developer("developer")
        .platforms(List.of("platform1", "platform2"))
        .uploadDateTime(LocalDateTime.now())
        .build();
    when(service.findVideoGameLikeName(any())).thenReturn(List.of(videogame));

    //WHEN
    List<VideoGame> result = useCase.findByName("name");

    //THEN
    assertEquals(result, List.of(videogame));

  }

  @Test
  public void cantFindVideoGameBecauseDoesntExist() {
    //GIVEN
    when(service.findVideoGameLikeName(any())).thenReturn(List.of());

    //WHEN
    assertThrows(NoSuchElementException.class, () -> useCase.findByName("name"));
  }
}
