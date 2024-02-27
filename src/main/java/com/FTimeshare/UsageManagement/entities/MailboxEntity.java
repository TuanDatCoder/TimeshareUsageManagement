package com.FTimeshare.UsageManagement.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "Mailbox")
@Data
@NoArgsConstructor
public class MailboxEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mailbox_id")
    private int mailboxId;

    @Column(name = "mailbox_description")
    private String mailboxDescription;

    @Column(name = "mailbox_type")
    private String mailboxType;

    @Column(name = "mailbox_status")
    private String mailboxStatus;

    @Column(name = "mailbox_send_date")
    private LocalDateTime mailboxSendDate;

    @ManyToOne
    @JoinColumn(name = "acc_id_send", referencedColumnName = "acc_id")
    private  AccountEntity accIdSend;

    @ManyToOne
    @JoinColumn(name = "acc_id_receive", referencedColumnName = "acc_id")
    private AccountEntity accIdReceive;
}
