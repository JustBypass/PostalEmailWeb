package com.mail.global.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class FolderEnvelopeDTO implements Serializable {
    private String email;
    private String folder;
}
