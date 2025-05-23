package com.example.forumservice.model;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Forum implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Category category;

    @OneToMany(mappedBy = "forum")
    private List<Topic> topics;

    @Column(nullable = false)
    private String name;

    private String description;

    private boolean moderated;

    private boolean allowAnonymousPosts;

    private int displayOrder;
}
