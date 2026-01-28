package org.apirest.talky.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "Page") // Specify table name if different from class name
@AllArgsConstructor
@NoArgsConstructor
@Data

public class PageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private LocalDateTime dateCreation;

    @OneToOne
    @JoinColumn(name = "id_User", unique = true) //FK
    private UserEntity user;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_Page") //FK
    private List<PostEntity> posts = new ArrayList<>();

    public void addPost(PostEntity post) {
        this.posts.add(post);
    }

    public void removePost(PostEntity post) {
        this.posts.remove(post);
    }
}
