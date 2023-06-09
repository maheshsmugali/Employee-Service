package com.pws.employees.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.context.annotation.Configuration;

import javax.persistence.*;
@Configuration
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_skill_xref")
public class UserSkillXref {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "skill_id")
    private Skill skill;

    @Enumerated(EnumType.STRING)
    @Column(name = "proficiency_level", nullable = false)
    private Keyword proficiencyLevel;

    public enum Keyword {
        Beginner, Intermediate, Expert
    }

    @Column(name = "is_active", nullable = false)
    @ColumnDefault("TRUE")
    private Boolean isActive;

}