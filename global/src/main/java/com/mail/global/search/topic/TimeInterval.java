package com.mail.global.search.topic;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TimeInterval {
    private LocalDate from;
    private LocalDate to;
}
