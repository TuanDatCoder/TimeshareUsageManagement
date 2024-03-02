package com.FTimeshare.UsageManagement.services;

import com.FTimeshare.UsageManagement.dtos.MailboxDto;
import com.FTimeshare.UsageManagement.dtos.NewsDto;
import com.FTimeshare.UsageManagement.entities.MailboxEntity;
import com.FTimeshare.UsageManagement.entities.NewsEntity;
import com.FTimeshare.UsageManagement.entities.ProductEntity;
import com.FTimeshare.UsageManagement.repositories.MailboxRepository;
import com.FTimeshare.UsageManagement.repositories.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MailboxService {

    @Autowired
    private MailboxRepository mailboxRepository;

    public List<MailboxEntity> getAllMailbox() {
        return mailboxRepository.findAll();
    }

    public List<MailboxEntity> getMailboxByAccIdSend(int userID) {

        return mailboxRepository.findByAccIdSend(userID);
    }
    public List<MailboxEntity> getMailboxByAccIdReceive(int userID) {

        return mailboxRepository.findByAccIdReceive(userID);
    }

}
