package org.apirest.talky.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "App_User") // Specify table name if different from class name
@AllArgsConstructor
@NoArgsConstructor
@Data

public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer age;
    private String mail;
    private String password;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private PageEntity userPage;

}
