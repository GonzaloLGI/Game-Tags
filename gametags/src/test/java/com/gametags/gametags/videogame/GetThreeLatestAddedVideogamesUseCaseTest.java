package com.gametags.gametags.videogame;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.gametags.gametags.application.videogame.LatestVideoGamesUseCase;
import com.gametags.gametags.domain.model.VideoGame;
import com.gametags.gametags.domain.services.VideoGameService;
import org.apache.commons.lang3.ObjectUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class GetThreeLatestAddedVideogamesUseCaseTest {

  @Mock
  VideoGameService service;

  @InjectMocks
  LatestVideoGamesUseCase latestVideogamesUseCase;

  @Test
  public void getThreeLatestAddedGames() {
    //GIVEN
    VideoGame firstVideoGame = VideoGame.builder().build();
    VideoGame secondVideoGame = VideoGame.builder().build();
    VideoGame thirdVideoGame = VideoGame.builder().build();
    List<VideoGame> latestVideogames = new ArrayList<>(List.of(firstVideoGame,secondVideoGame,thirdVideoGame));

    when(service.findThreeLatestVideogames()).thenReturn(latestVideogames);
    //WHEN
    List<VideoGame> returned = latestVideogamesUseCase.threeLatestVideogames();
    //THEN
    assertTrue(ObjectUtils.isNotEmpty(returned));

  }

}
