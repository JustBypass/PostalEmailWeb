package com.mail.global.search.topic;

import lombok.Data;

import java.io.Serializable;

@Data
public class SentTopicLocalFilterDTO implements Serializable {
    private String email;
    private String toWhom;
    private TimeInterval interval;
    private boolean containsAttachment;
}
