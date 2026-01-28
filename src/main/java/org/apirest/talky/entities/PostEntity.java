package org.apirest.talky.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Post") // Specify table name if different from class name
@AllArgsConstructor
@NoArgsConstructor
@Data

public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
