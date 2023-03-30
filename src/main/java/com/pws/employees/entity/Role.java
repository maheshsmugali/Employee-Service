package com.pws.employees.entity;

import com.pws.employees.utilities.AuditModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "role")
public class Role extends AuditModel {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "r_name", unique = true, nullable = false)
    private String name;
    @Column(nullable = false)
    private Boolean isActive;

}
