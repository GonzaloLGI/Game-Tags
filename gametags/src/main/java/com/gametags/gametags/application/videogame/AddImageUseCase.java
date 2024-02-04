package com.gametags.gametags.application.videogame;

import com.gametags.gametags.domain.model.VideoGame;
import com.gametags.gametags.domain.services.VideoGameService;
import lombok.extern.slf4j.Slf4j;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Component
@Slf4j
public class AddImageUseCase {

    @Autowired
    private VideoGameService service;

    public VideoGame addImage(MultipartFile image, UUID id) throws IOException {
        log.info("[START] Add Image");
        VideoGame previous = service.findOneVideoGame(id);
        if (Objects.isNull(previous.getId())) {
            log.info("[STOP] createVideoGame");
            return previous;
        }
        log.info("Image name: " + StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename())));
        Binary imageCover = new Binary(BsonBinarySubType.BINARY, image.getBytes());
        log.info("Image cover data size: " + imageCover.length());
        previous.setImageData(imageCover);
        log.info("[STOP] Add Image");
        return service.updateVideoGame(previous);
    }
}
