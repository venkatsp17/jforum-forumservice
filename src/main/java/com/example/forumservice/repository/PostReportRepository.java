package com.example.forumservice.repository;

import com.example.forumservice.model.PostReport;
import com.example.forumservice.model.PostReportStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostReportRepository extends JpaRepository<PostReport, Long> {

    List<PostReport> findByStatus(PostReportStatus status);
}
