package com.example.forumservice.service;

import com.example.forumservice.constant.ApplicationConstants;
import com.example.forumservice.constant.ErrorMessageConstants;
import com.example.forumservice.dto.PaginatedResponseDTO;
import com.example.forumservice.dto.PostReportDTO;
import com.example.forumservice.exception.ResourceNotFoundException;
import com.example.forumservice.model.Post;
import com.example.forumservice.model.PostReport;
import com.example.forumservice.model.PostReportStatus;
import com.example.forumservice.repository.PostReportRepository;
import com.example.forumservice.repository.PostRepository;
import com.example.forumservice.repository.TopicRepository;
import com.example.forumservice.repository.ForumRepository;
import com.example.forumservice.utils.ModelMapperUtils;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final PostReportRepository postReportRepository;
    private final TopicRepository topicRepository;
    private final ForumRepository forumRepository;

    public PostService(PostRepository postRepository,
                       PostReportRepository postReportRepository,
                       TopicRepository topicRepository,
                       ForumRepository forumRepository) {
        this.postRepository = postRepository;
        this.postReportRepository = postReportRepository;
        this.topicRepository = topicRepository;
        this.forumRepository = forumRepository;
    }

    public void reportPost(Long postId, String description, Long userId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstants.POST_NOT_FOUND));

        PostReport postReport = new PostReport();
        postReport.setPost(post);
        postReport.setDescription(description);
        postReport.setReportDate(new Date());
        postReport.setUserId(userId);
        postReport.setStatus(PostReportStatus.UNRESOLVED);

        postReportRepository.save(postReport);
    }

    public List<PostReport> getUnresolvedPostReports() {
        return postReportRepository.findByStatus(PostReportStatus.UNRESOLVED);
    }

    public PaginatedResponseDTO<PostReportDTO> getPaginatedResolvedPostReports(int page) {

        int pageSize = ApplicationConstants.RESOLVED_REPORTS_PAGE_SIZE;

        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by("reportDate").descending());

        Page<PostReport> reportsPage = postReportRepository.findByStatus(PostReportStatus.RESOLVED, pageable);

        List<PostReportDTO> reportsDTO = reportsPage.getContent().stream()
                .map(ModelMapperUtils::mapToPostReportDTO)
                .collect(Collectors.toList());

        PaginatedResponseDTO<PostReportDTO> paginatedResponse = new PaginatedResponseDTO<>();
        paginatedResponse.setPage(page);
        paginatedResponse.setReports(reportsDTO);

        return paginatedResponse;
    }

    public void resolvePostReport(Long reportId, Long userId) {

        PostReport report = postReportRepository.findById(reportId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstants.POST_REPORT_NOT_FOUND));

        report.setStatus(PostReportStatus.RESOLVED);
        postReportRepository.save(report);
    }

    public void deletePostReport(Long reportId) {

        PostReport report = postReportRepository.findById(reportId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstants.POST_REPORT_NOT_FOUND));

        postReportRepository.delete(report);
    }

    public Post getLastPostByTopicId(Long topicId) {

        if (!topicRepository.existsById(topicId)) {
            throw new ResourceNotFoundException(ErrorMessageConstants.TOPIC_NOT_FOUND);
        }

        return postRepository.findFirstByTopicIdOrderByCreatedAtDesc(topicId);
    }

    public Post getLastPostByForumId(Long forumId) {

        if (!forumRepository.existsById(forumId)) {
            throw new ResourceNotFoundException(ErrorMessageConstants.FORUM_NOT_FOUND);
        }

        return postRepository.findFirstByForumIdOrderByCreatedAtDesc(forumId);
    }
}
