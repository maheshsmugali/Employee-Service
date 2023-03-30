package com.pws.employees.entity;

import com.pws.employees.utilities.AuditModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Permission extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;

    @Column(name = "is_active", nullable = true)
    private Boolean isActive;

    @Column(name = "is_view",nullable = false)
    private Boolean isView;

    @Column(name = "is_add",nullable = false)
    private Boolean isAdd;

    @Column(name = "is_update",nullable = false)
    private Boolean isUpdate;

    @Column(name = "is_delete",nullable = false)
    private Boolean isDelete;

    @ManyToOne
    @JoinColumn(name = "model_id",nullable = false)
    private Model model;
    @ManyToOne
    @JoinColumn(name = "role_id",nullable = false)
//    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, orphanRemoval = true)

    private Role role;
}




//    @Column(name = "updated_at",nullable = false)
//    private Date updatedAt;
//    @Column(name = "created_at",nullable = false)
//    private Date createdAt;
//    @Column(name = "created_by",nullable = false)
//    private Integer createdBy;
//    @Column(name = "updated_by",nullable = false)
//    private Integer updatedBy;
