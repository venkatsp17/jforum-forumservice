package com.example.forumservice.repository;

import com.example.forumservice.model.PostReport;
import com.example.forumservice.model.PostReportStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostReportRepository extends JpaRepository<PostReport, Long> {

    List<PostReport> findByStatus(PostReportStatus status);

    Page<PostReport> findByStatus(PostReportStatus status, Pageable pageable);
}
