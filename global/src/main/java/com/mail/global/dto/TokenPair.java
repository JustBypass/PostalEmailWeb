package com.mail.global.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TokenPair implements Serializable {
    private String accessToken;
    private String refreshToken;
}
