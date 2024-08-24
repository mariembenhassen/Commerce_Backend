package com.magazin.commerce.dto;

import com.magazin.commerce.enums.UserRole;
import lombok.Data;

@Data
public class SignupRequest {
    private  String email;
    private String password;
    private String name;
    private UserRole role;

}
