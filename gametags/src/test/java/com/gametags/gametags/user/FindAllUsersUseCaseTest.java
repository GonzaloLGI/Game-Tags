package com.gametags.gametags.user;

import com.gametags.gametags.application.classification.FindAllClassificationsUseCase;
import com.gametags.gametags.application.user.FindAllUsersUseCase;
import com.gametags.gametags.domain.model.Classification;
import com.gametags.gametags.domain.model.User;
import com.gametags.gametags.domain.services.ClassificationService;
import com.gametags.gametags.domain.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FindAllUsersUseCaseTest {
    @InjectMocks
    FindAllUsersUseCase useCase;

    @Mock
    UserService service;

    @Test
    public void findAll(){
        //GIVEN
        User user1 = User.builder()
                .id(UUID.randomUUID())
                .username("username1")
                .email("email1")
                .password("password1")
                .country("country1")
                .comments(List.of("comment1","comment2"))
                .build();
        User user2 = User.builder()
                .id(UUID.randomUUID())
                .username("username2")
                .email("email2")
                .password("password2")
                .country("country2")
                .comments(List.of("comment1","comment2"))
                .build();
        List<User> list = List.of(user1,user2);
        when(useCase.findAllUsers()).thenReturn(list);

        //WHEN
        List<User> result = useCase.findAllUsers();

        //THEN
        assertEquals(list.size(),result.size());
        assertEquals(list.get(0).getId(),result.get(0).getId());
    }
}
