package com.example.forumservice.repository;

import com.example.forumservice.model.Forum;
import com.example.forumservice.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ForumRepository extends JpaRepository<Forum, Long> {

    List<Forum> findByCategoryOrderByDisplayOrderAsc(Category category);

    @Query("SELECT COUNT(p) FROM Post p")
    int getTotalMessages();

    @Query("SELECT COUNT(t) FROM Topic t WHERE t.forum.id = :forumId")
    long countTopicsByForumId(@Param("forumId") Long forumId);

    @Query("SELECT COUNT(p) FROM Post p WHERE p.topic.forum.id = :forumId")
    long countMessagesByForumId(@Param("forumId") Long forumId);
}
