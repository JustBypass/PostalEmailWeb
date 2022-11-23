package com.mail.settings.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class ConfigurationSettingsDTO implements Serializable {
    private String email;
    private Theme theme;
    private String language;
    private InterfaceType interfaceType;
}
