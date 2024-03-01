package com.FTimeshare.UsageManagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MailboxDto {
 private int mailboxId;
 private String mailboxDescription;
 private String mailboxType;
 private String mailboxStatus;
 private LocalDateTime mailboxSendDate;
 private  int accIdSend;
 private int accIdReceive;
}
