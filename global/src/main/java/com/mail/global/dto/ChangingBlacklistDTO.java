package com.mail.global.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ChangingBlacklistDTO implements Serializable {
    private String who;
    private String whom;
}
