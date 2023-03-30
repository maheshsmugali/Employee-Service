package com.pws.employees.dto;

import java.util.List;
import com.pws.employees.entity.Permission;
import com.pws.employees.entity.Role;
import com.pws.employees.entity.Skill;
import com.pws.employees.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBasicDetailsDTO {

    private User user;

    private List<Role> roleList;

    private List<Permission> permissionList;

    private List<Skill> skilllist;

}