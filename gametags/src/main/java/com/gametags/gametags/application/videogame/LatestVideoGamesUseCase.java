package com.gametags.gametags.application.videogame;

import com.gametags.gametags.domain.model.VideoGame;
import com.gametags.gametags.domain.services.VideoGameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class LatestVideoGamesUseCase {

    @Autowired
    private VideoGameService service;

    public List<VideoGame> threeLatestVideogames() {
        log.info("[START] threeLatestVideogames");
        log.info("[STOP] threeLatestVideogames");
        return service.findThreeLatestVideogames();
    }
}
