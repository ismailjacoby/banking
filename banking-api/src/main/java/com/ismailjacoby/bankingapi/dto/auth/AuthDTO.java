package com.ismailjacoby.bankingapi.dto.auth;

import com.ismailjacoby.bankingapi.models.enums.UserRole;
import lombok.Builder;

@Builder
public record AuthDTO (
        String username,
        String token,
        UserRole role
){
}
