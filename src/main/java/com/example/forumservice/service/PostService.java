package com.example.forumservice.service;

import com.example.forumservice.constant.ErrorMessageConstants;
import com.example.forumservice.exception.ResourceNotFoundException;
import com.example.forumservice.model.Post;
import com.example.forumservice.model.PostReport;
import com.example.forumservice.repository.PostRepository;
import com.example.forumservice.repository.PostReportRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final PostReportRepository postReportRepository;

    public PostService(PostRepository postRepository, PostReportRepository postReportRepository) {
        this.postRepository = postRepository;
        this.postReportRepository = postReportRepository;
    }

    public void reportPost(Long postId, String description, Long userId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstants.POST_NOT_FOUND));

        PostReport postReport = new PostReport();
        postReport.setPost(post);
        postReport.setDescription(description);
        postReport.setReportDate(new Date());
        postReport.setUserId(userId);

        postReportRepository.save(postReport);
    }
}
