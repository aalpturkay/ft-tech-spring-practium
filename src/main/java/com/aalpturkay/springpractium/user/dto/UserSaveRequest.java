package com.aalpturkay.springpractium.user.dto;

import lombok.Data;

@Data
public class UserSaveRequest {
    private String name;

    private String surname;

    private String email;

    private String phoneNumber;
}
