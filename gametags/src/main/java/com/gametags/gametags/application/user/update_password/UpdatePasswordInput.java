package com.gametags.gametags.application.user.update_password;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdatePasswordInput {

    private String newPassword;

    private String existingPassword;

}
