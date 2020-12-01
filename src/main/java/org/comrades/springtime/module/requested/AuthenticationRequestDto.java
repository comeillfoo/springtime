package org.comrades.springtime.module.requested;

import lombok.Data;

@Data
public class AuthenticationRequestDto {
    private String login;
    private String password;
}
