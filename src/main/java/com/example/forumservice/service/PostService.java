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

    public void resolvePostReport(Long reportId) {

        PostReport report = postReportRepository.findById(reportId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstants.POST_REPORT_NOT_FOUND));

        report.setStatus(PostReportStatus.RESOLVED);
        postReportRepository.save(report);
    }
}
