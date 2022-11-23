package com.mail.global.search.input;

import lombok.Data;

import java.io.Serializable;

@Data
public class SearchLocalFilterDTO implements Serializable {
    private boolean containsAttachment;
    private boolean marked;
    private String input;
}
