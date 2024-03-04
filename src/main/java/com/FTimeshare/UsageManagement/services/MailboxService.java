package com.FTimeshare.UsageManagement.services;
import com.FTimeshare.UsageManagement.dtos.MailboxDto;
import com.FTimeshare.UsageManagement.entities.AccountEntity;
import com.FTimeshare.UsageManagement.entities.MailboxEntity;
import com.FTimeshare.UsageManagement.repositories.AccountRepository;
import com.FTimeshare.UsageManagement.repositories.MailboxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MailboxService {
    @Autowired
    private AccountRepository accountRepository;

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



    public MailboxDto submitMail(MailboxDto mailbox) {
        MailboxEntity mailboxEntity = new MailboxEntity();
        // Set properties of bookingEntity from bookingRequest
        mailboxEntity.setMailboxId(mailbox.getMailboxId());
        mailboxEntity.setMailboxDescription(mailbox.getMailboxDescription());
        mailboxEntity.setMailboxType(mailbox.getMailboxType());
        mailboxEntity.setMailboxStatus(mailbox.getMailboxStatus());
        mailboxEntity.setMailboxSendDate(mailbox.getMailboxSendDate());
        // Assuming you have UserRepository and ProductRepository
        AccountEntity accountEntity = accountRepository.findById(mailbox.getAccIdSend()).orElse(null);
        AccountEntity accountEntityy = accountRepository.findById(mailbox.getAccIdReceive()).orElse(null);

        if (accountEntity != null && accountEntityy != null) {
            mailboxEntity.setAccIdSend(accountEntity);
            mailboxEntity.setAccIdReceive(accountEntityy);
            // Set other properties as needed

            // Save the bookingEntity
            MailboxEntity savedMailboxEntity = mailboxRepository.save(mailboxEntity);

            // Convert and return the saved entity as DTO
            return convertToDto(savedMailboxEntity);
        } else {
            // Handle case where user or product is not found
            return null;
        }
    }

    private MailboxDto convertToDto(MailboxEntity mailboxEntity) {
        // Your existing DTO conversion logic
        return new MailboxDto(
                mailboxEntity.getMailboxId(),
                mailboxEntity.getMailboxDescription(),
                mailboxEntity.getMailboxType(),
                mailboxEntity.getMailboxStatus(),
                mailboxEntity.getMailboxSendDate(),
                mailboxEntity.getAccIdSend().getAccID(),
                mailboxEntity.getAccIdReceive().getAccID());
    }

    public MailboxDto deleteMailBox(int mailboxId) {
        Optional<MailboxEntity> mailboxEntityOptional = mailboxRepository.findById(mailboxId);

        if (mailboxEntityOptional.isPresent()) {
            MailboxEntity mailboxEntity = mailboxEntityOptional.get();
            mailboxRepository.delete(mailboxEntity);
            return convertToDto(mailboxEntity);
        } else {
            return null;
        }
    }
}
