package Entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "ProjectType")
@NoArgsConstructor
public class ProjectTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String projectTypeID;

    @Column(name = "projectTypeName")
    private String projectTypeName;

}