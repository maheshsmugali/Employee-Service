package com.pws.employees.service;

import com.pws.employees.dto.SignUpDto;
import com.pws.employees.dto.UpdatePasswordDTO;
import com.pws.employees.dto.UserBasicDetailsDTO;
import com.pws.employees.dto.UserSkillXrefDTO;
import com.pws.employees.entity.*;
import com.pws.employees.exception.PWSException;
import com.pws.employees.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleXrefRepository userRoleXRefRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private SkillRepository skillRepository;
    @Autowired
    private UserSkillXrefRepository userSkillXrefRepository;
    @Autowired
    PermissionRepository permissionRepository;

    @Override
    public void employeSignUp(SignUpDto signUpDTO) throws Exception {

        if (!isStrongPassword(signUpDTO.getPassword())) {
            throw new Exception("Password is not strong , at least one uppercase letter, one lowercase letter, one digit, and one special character needed");
        }

        Optional<User> optionalUser = userRepository.findByEmail(signUpDTO.getEmail());
        if (optionalUser.isPresent()) throw new Exception("User Already Exist with Email : " + signUpDTO.getEmail());
        User user = new User();
        user.setDob(signUpDTO.getDob());
        user.setFirstName(signUpDTO.getFirstName());
        user.setIsActive(true);
        user.setLastName(signUpDTO.getLastName());
        user.setEmail(signUpDTO.getEmail());
        user.setPhoneNumber(signUpDTO.getPhoneNumber());
        PasswordEncoder encoder = new BCryptPasswordEncoder(8);
        // Set new password
        user.setPassword(encoder.encode(signUpDTO.getPassword()));
        userRepository.save(user);

        Optional<Role> optionalRole = roleRepository.findByRoleName(signUpDTO.getRoleName());
        Role role = optionalRole.get();
        UserRoleXRef userRoleRef = new UserRoleXRef();
        userRoleRef.setRole(role);
        userRoleRef.setUser(user);
        userRoleRef.setIsActive(true);
        userRoleXRefRepository.save(userRoleRef);
    }

    @Override
    public Optional<User> fetchById(Integer id) {
        return userRepository.findById(id);
    }

    private boolean isStrongPassword(String password) {
        boolean hasUppercase = false;
        boolean hasLowercase = false;
        boolean hasDigit = false;
        boolean hasSpecialChar = false;

        // check for at least one uppercase letter, one lowercase letter, one digit, and one special character
        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);
            if (Character.isUpperCase(ch)) {
                hasUppercase = true;
            } else if (Character.isLowerCase(ch)) {
                hasLowercase = true;
            } else if (Character.isDigit(ch)) {
                hasDigit = true;
            } else if (isSpecialChar(ch)) {
                hasSpecialChar = true;
            }
        }
        // check if password meets all criteria
        return password.length() >= 8 && hasUppercase && hasLowercase && hasDigit && hasSpecialChar;
    }

    private boolean isSpecialChar(char ch) {
        String specialChars = "!@#$%^&*()_-+=[{]};:<>|./?";
        return specialChars.contains(Character.toString(ch));
    }

    @Override
    public Skill createSkills(Skill skill) {
        return skillRepository.save(skill);
    }

    @Override
    public void addSkillToUser(UserSkillXrefDTO userSkillXrefDTO) throws Exception {
        UserSkillXref userSkillXref = new UserSkillXref();
        Optional<User> optionalUser = userRepository.findById(userSkillXrefDTO.getUserId());
        if (optionalUser.isPresent()) {
            userSkillXref.setUser(optionalUser.get());
        } else {
            throw new Exception("User Doest Exist");
        }
        Optional<Skill> optionalskill = skillRepository.findById(userSkillXrefDTO.getSkillId());
        if (optionalskill.isPresent()) {
            userSkillXref.setSkill(optionalskill.get());
        } else {
            throw new Exception("skill Doest Exist");
        }
        userSkillXref.setId(userSkillXrefDTO.getId());
        userSkillXref.setProficiencyLevel(userSkillXrefDTO.getSkillLevel());
        userSkillXref.setIsActive(userSkillXrefDTO.getIsActive());

        userSkillXrefRepository.save(userSkillXref);
    }

    @Override
    public UserBasicDetailsDTO getUserBasicInfoAfterLoginSuccess(String email) throws Exception {
        Optional<User> optionalUser = userRepository.findUserByEmail(email);
        if (!optionalUser.isPresent())
            throw new Exception("User Already Exist with Email : " + email);


        User user = optionalUser.get();
        UserBasicDetailsDTO userBasicDetailsDTO = new UserBasicDetailsDTO();
        userBasicDetailsDTO.setUser(user);

        List<Role> roleList = userRoleXRefRepository.findAllUserRoleByUserId(user.getId());
        userBasicDetailsDTO.setRoleList(roleList);
        List<Permission> permissionList = null;
        if (roleList.size() > 0)
            permissionList = permissionRepository.getAllUserPermisonsByRoleId(roleList.get(0).getId());

        userBasicDetailsDTO.setPermissionList(permissionList);
        List<Skill> skilllist = userSkillXrefRepository.fetchuserSkillsByid(user.getId());
        userBasicDetailsDTO.setSkilllist(skilllist);


        return userBasicDetailsDTO;
    }

    @Override
    public void updateUserPassword(UpdatePasswordDTO userPasswordDTO) throws Exception {
        Optional<User> optionalUser = userRepository.findUserByEmail(userPasswordDTO.getUserEmail());

        PasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = null;
        if (!optionalUser.isPresent()) {
            throw new Exception("User Not present ");
        }
        user = optionalUser.get();
        if (encoder.matches(userPasswordDTO.getOldPassword(), user.getPassword())) {
            if (userPasswordDTO.getNewPassword().equals(userPasswordDTO.getConfirmNewPassword())) {

                user.setPassword(encoder.encode(userPasswordDTO.getConfirmNewPassword()));
                userRepository.save(user);
            } else {
                throw new Exception("new password and confirm password doesnot match ");
            }

        } else {
            throw new Exception("oldpassword not matched");
        }

    }

    @Override
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<Skill> fetchuserSkillsByid(Integer id) throws Exception {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
        } else {
            throw new PWSException("email id " + id + "doesnot exist");
        }


        return userSkillXrefRepository.fetchuserSkillsByid(id);
    }

    @Override
    public Page<Skill> getAllSkills(Integer pageNumber, Integer pageSize) throws PWSException {
        Sort sort = Sort.by(Sort.Direction.ASC, "skillName");
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        return skillRepository.findAll(pageable);
    }

}