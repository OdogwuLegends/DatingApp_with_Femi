package com.legends.promiscuous.repositories;

import com.legends.promiscuous.models.DirectMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DirectMessageRepository extends JpaRepository<DirectMessage, Long> {
}
