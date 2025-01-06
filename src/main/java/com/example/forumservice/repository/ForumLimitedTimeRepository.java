package com.example.forumservice.repository;

import com.example.forumservice.model.Forum;
import com.example.forumservice.model.ForumLimitedTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ForumLimitedTimeRepository extends JpaRepository<ForumLimitedTime, Long> {

    Optional<ForumLimitedTime> findByForum(Forum forum);
}
