package com.example.forumservice.repository;

import com.example.forumservice.model.Forum;
import com.example.forumservice.model.Category;
import com.example.forumservice.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ForumRepository extends JpaRepository<Forum, Long> {

    List<Forum> findByCategoryOrderByDisplayOrderAsc(Category category);

    @Query("SELECT COUNT(p) FROM Post p")
    int getTotalMessages();
}
