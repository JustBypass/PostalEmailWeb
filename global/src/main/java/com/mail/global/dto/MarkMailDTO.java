package com.mail.global.dto;

import lombok.Data;

@Data
public class MarkMailDTO {
    private String folder;

    private MailInner[] messages;
    @Data
    public static class MailInner{
        private long uuid;
        private boolean mailOperationQuota;
    }
}
