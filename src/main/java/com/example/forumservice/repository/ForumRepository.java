package com.example.forumservice.repository;

import com.example.forumservice.model.Forum;
import com.example.forumservice.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ForumRepository extends JpaRepository<Forum, Long> {

    @Query("SELECT COUNT(p) FROM Post p")
    int getTotalMessages();
}
