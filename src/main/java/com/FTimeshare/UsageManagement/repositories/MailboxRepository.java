package com.FTimeshare.UsageManagement.repositories;

import com.FTimeshare.UsageManagement.entities.MailboxEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailboxRepository extends JpaRepository<MailboxEntity, Integer> {
}

