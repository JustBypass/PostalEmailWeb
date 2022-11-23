package com.mail.global.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class RemovingToSpamFolderEmailDTO implements Serializable {
    private String email;
    private int uuid;
    private String spamFolder;
    private String folder;
}
