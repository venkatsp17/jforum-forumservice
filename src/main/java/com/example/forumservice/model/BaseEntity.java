package com.example.forumservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
    @JsonIgnore
    @CreationTimestamp
    @CreatedDate
    @Column(name = "created_on", updatable = false, nullable = false)
    private LocalDateTime createdOn;

    @JsonIgnore
    @UpdateTimestamp
    @LastModifiedDate
    @Column(name = "updated_on", nullable = false)
    private LocalDateTime updatedOn;

}
