package com.example.forumservice.repository;

import com.example.forumservice.model.Topic;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {

    @Query("""
        SELECT t.id AS topicId,
               t.subject AS subject,
               COUNT(p.id) AS postCount,
               MAX(p.id) AS lastPostId,
               MAX(p.createdAt) AS lastPostDate,
               t.viewCount AS viewCount
        FROM Topic t
        LEFT JOIN t.posts p
        WHERE t.forum.id = :forumId
        GROUP BY t.id, t.subject, t.viewCount
        """)
    List<Object[]> findTopicsWithPostDetailsByForumId(Long forumId);
}
