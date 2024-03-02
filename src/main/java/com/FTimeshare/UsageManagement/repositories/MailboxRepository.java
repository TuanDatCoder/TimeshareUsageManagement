package com.FTimeshare.UsageManagement.repositories;

import com.FTimeshare.UsageManagement.entities.MailboxEntity;
import com.FTimeshare.UsageManagement.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MailboxRepository extends JpaRepository<MailboxEntity, Integer> {

    @Query("SELECT p FROM MailboxEntity p WHERE p.accIdSend.accID  = :accID")
    List<MailboxEntity> findByAccIdSend(@Param("accID") int accID);
    @Query("SELECT p FROM MailboxEntity p WHERE p.accIdReceive.accID  = :accID")
    List<MailboxEntity> findByAccIdReceive(@Param("accID") int accID);
}

