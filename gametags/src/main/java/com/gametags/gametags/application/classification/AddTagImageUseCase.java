package com.gametags.gametags.application.classification;

import com.gametags.gametags.domain.model.Classification;
import com.gametags.gametags.domain.model.VideoGame;
import com.gametags.gametags.domain.services.ClassificationService;
import com.gametags.gametags.domain.services.VideoGameService;
import lombok.extern.slf4j.Slf4j;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@Slf4j
public class AddTagImageUseCase {

    @Autowired
    private ClassificationService service;

    @Autowired
    private VideoGameService videoGameService;

    private static final String STOP = "[STOP] AddTagImage";

    public Classification addImage(MultipartFile image, UUID id, UUID gameId) throws IOException {
        log.info("[START] AddTagImage");
        Classification previous = service.findOneClassification(id);
        if (Objects.isNull(previous.getId())) {
            log.info(STOP);
            return previous;
        }
        log.info("Image name: " + StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename())));
        Binary imageTag = new Binary(BsonBinarySubType.BINARY, image.getBytes());
        log.info("Image cover data size: " + imageTag.length());
        previous.setImageData(imageTag);
        VideoGame gameToUpdate = videoGameService.findOneVideoGame(gameId);
        if (Objects.isNull(gameToUpdate.getId())) {
            log.info(STOP);
            return previous;
        }
        List<Classification> updatedClassifications = gameToUpdate.getClassifications().stream()
                .map(classification -> addTagImage(classification, id, imageTag)).toList();
        gameToUpdate.getClassifications().removeAll(gameToUpdate.getClassifications());
        gameToUpdate.getClassifications().addAll(updatedClassifications);
        videoGameService.updateVideoGame(gameToUpdate);
        log.info(STOP);
        return service.updateClassification(previous);
    }

    private Classification addTagImage(Classification classification, UUID tagId, Binary tagImage) {
        if(classification.getId().equals(tagId)){
            classification.setImageData(tagImage);
            service.updateClassification(classification);
        }
        return classification;
    }
}
