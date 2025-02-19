package com.example.forumservice.repository;

import com.example.forumservice.model.Post;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Post findFirstByTopicIdOrderByCreatedAtDesc(Long topicId);

    Post findFirstByForumIdOrderByCreatedAtDesc(Long forumId);

    List<Post> findByTopicId(Long topicId);
}
