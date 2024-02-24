package com.gametags.gametags.application.user;

import com.gametags.gametags.domain.model.User;
import com.gametags.gametags.domain.model.VideoGame;
import com.gametags.gametags.domain.services.UserService;
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
public class AddProfileImageUseCase {

    @Autowired
    private UserService service;

    public User addImage(MultipartFile image, UUID id) throws IOException {
        log.info("[START] Add Profile Image");
        User existing = service.findOneUserById(id);
        if (Objects.isNull(existing.getId())) {
            log.info("[STOP] Add Profile Image");
            return existing;
        }
        log.info("Image name: " + StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename())));
        Binary profileIimage = new Binary(BsonBinarySubType.BINARY, image.getBytes());
        log.info("Image cover data size: " + profileIimage.length());
        existing.setProfileImageData(profileIimage);
        log.info("[STOP] Add Profile Image");
        return service.updateUser(existing);
    }
}
