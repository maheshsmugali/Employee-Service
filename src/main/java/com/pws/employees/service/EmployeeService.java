package com.pws.employees.service;

import com.pws.employees.dto.SignUpDto;
import com.pws.employees.dto.UpdatePasswordDTO;
import com.pws.employees.dto.UserBasicDetailsDTO;
import com.pws.employees.dto.UserSkillXrefDTO;
import com.pws.employees.entity.Skill;
import com.pws.employees.entity.User;
import com.pws.employees.exception.PWSException;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    void employeSignUp(SignUpDto signUpDTO) throws Exception;

    Optional<User> fetchById(Integer id);

    Skill createSkills(Skill skill);

    void addSkillToUser(UserSkillXrefDTO userSkillXrefDTO) throws Exception;

    UserBasicDetailsDTO getUserBasicInfoAfterLoginSuccess(String email) throws Exception;

    void updateUserPassword(UpdatePasswordDTO userPasswordDTO) throws Exception;

    void delete(Integer id);


    List<Skill> fetchuserSkillsByid(Integer id) throws Exception;

    Page<Skill> getAllSkills(Integer pageNumber, Integer pageSize) throws PWSException;
}

