package com.example.forumservice.repository;

import com.example.forumservice.model.ModerationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModerationLogRepository extends JpaRepository<ModerationLog, Long> {
}
