package com.mail.global.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Data
public class ScheduledMail implements Serializable {
   private Post post;
   private LocalDate dateToSend;
}
