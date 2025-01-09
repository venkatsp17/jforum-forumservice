package com.example.forumservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Topic implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Forum forum;

    @OneToMany(mappedBy = "topic")
    private List<Post> posts;

    @Column(nullable = false)
    private String subject;

    @Column(nullable = false)
    private Date createdAt;

    @Column(nullable = false)
    private Integer viewCount = 0;
}
