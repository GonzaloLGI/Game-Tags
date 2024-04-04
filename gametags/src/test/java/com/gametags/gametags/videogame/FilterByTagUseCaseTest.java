package com.gametags.gametags.videogame;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.gametags.gametags.application.videogame.filter_videogames.FilterByTagUseCase;
import com.gametags.gametags.domain.model.Classification;
import com.gametags.gametags.domain.model.VideoGame;
import com.gametags.gametags.domain.services.VideoGameService;
import org.apache.commons.lang3.ObjectUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class FilterByTagUseCaseTest {
  @Mock
  VideoGameService service;

  @InjectMocks
  FilterByTagUseCase filterByTagUseCase;

  @Test
  public void filterByTag() {
    //GIVEN
    VideoGame videogame1 = VideoGame.builder()
        .id(UUID.randomUUID())
        .name("name1")
        .developer("Rockstar")
        .platforms(List.of("PS2", "XBOX"))
        .uploadDateTime(LocalDateTime.now())
        .classifications(List.of(Classification.builder().tag("18").build()))
        .build();
    VideoGame videogame2 = VideoGame.builder()
        .id(UUID.randomUUID())
        .name("name1")
        .developer("Rockstar")
        .platforms(List.of("PS2", "XBOX"))
        .uploadDateTime(LocalDateTime.now())
        .classifications(List.of(Classification.builder().tag("12").build()))
        .build();
    List<VideoGame> list = List.of(videogame2);

    when(service.findAllVideoGamesByTag(Arrays.asList("12","T","M","C"))).thenReturn(list);
    //WHEN
    List<VideoGame> returned = filterByTagUseCase.videoGamesByTag("12");
    //THEN
    assertEquals(null, 1, returned.size());
    assertTrue(returned.get(0).getClassifications().get(0).getTag().equals("12"));

  }

  @Test
  public void invalidTag() {
    //GIVEN
    when(service.findAllVideoGamesByTag(Arrays.asList())).thenReturn(List.of());

    //WHEN
    List<VideoGame> returned = filterByTagUseCase.videoGamesByTag("wefqwe");

    //THEN
    assertEquals(null, 0, returned.size());

  }

}
