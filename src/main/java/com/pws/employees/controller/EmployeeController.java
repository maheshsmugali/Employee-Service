package com.pws.employees.controller;

import com.pws.employees.dto.*;
import com.pws.employees.entity.Skill;
import com.pws.employees.entity.User;
import com.pws.employees.exception.PWSException;
import com.pws.employees.jwtconfiguration.JwtUtil;
import com.pws.employees.service.EmployeeService;
import com.pws.employees.utilities.ApiSuccess;
import com.pws.employees.utilities.CommonUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
@RequestMapping("/employees")
@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Operation(summary = "Authenticating the User")
    @PostMapping("/public/authenticate")
    public String generateToken(@RequestBody LoginDto loginDto) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(),loginDto.getPassword())
            );
        } catch (Exception ex) {
            throw new Exception("inavalid username/password");
        }
        return new String("token:" + jwtUtil.generateToken(loginDto.getEmail()));
    }

    @PostMapping("/public/signup")
    public ResponseEntity<String> employeeSignUp(@RequestBody SignUpDto signUpDto) throws Exception {

        employeeService.employeSignUp(signUpDto);
        return new ResponseEntity<String>("Employee signedUp Successfully", HttpStatus.OK);
    }

    @PostMapping("/skills")
    public ResponseEntity<String> addSkills(@RequestBody Skill skill) throws Exception{
        employeeService.createSkills(skill);
        return ResponseEntity.ok("skills added successfully");
    }
    @GetMapping("/fetchbyemployeeid/{id}")
    public Optional<User> fetchEmployeeById(@PathVariable Integer id){
        return employeeService.fetchById(id);
    }

    @PostMapping("/private/user/skill")
    public ResponseEntity<Object> addSkillToUser(@RequestBody UserSkillXrefDTO userSkillXrefDTO) throws Exception {
        employeeService.addSkillToUser(userSkillXrefDTO);
        return CommonUtils.buildResponseEntity(new ApiSuccess(HttpStatus.OK));
    }
    @GetMapping("/private/user")
    public ResponseEntity<Object> getUserBasicInfoAfterLoginSuccess(@Parameter(description = "Enter Email",example = "Aryaa@Gfmail.com",required = true) @RequestParam  String email) throws  Exception {
        UserBasicDetailsDTO userBasicDetailsDTO = employeeService.getUserBasicInfoAfterLoginSuccess(email);
        return CommonUtils.buildResponseEntity(new ApiSuccess(HttpStatus.OK, userBasicDetailsDTO));
    }


    @Operation(summary = "Update user password")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Password Updated Successfully",
                    content = { @Content(mediaType = "application/json"    ) }),
            @ApiResponse(responseCode = "400", description = "Invalid UserName/Password supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",content = @Content) })
    @PutMapping("private/update/user/password")
    public ResponseEntity<Object> updateUserPassword(@RequestBody UpdatePasswordDTO userPasswordDTO)throws Exception{
        employeeService.updateUserPassword(userPasswordDTO);
        return CommonUtils.buildResponseEntity(new ApiSuccess(HttpStatus.OK));
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable("id") Integer id) {
        employeeService.delete(id);
        return CommonUtils.buildResponseEntity(new ApiSuccess(HttpStatus.OK));
    }
    @GetMapping("/private/user-skills-by-id/{id}")
    public ResponseEntity<Object> fetchUserSkillsByid(@PathVariable Integer id) throws Exception {
        List<Skill> listOfActiveUserSkillXref=  employeeService.fetchuserSkillsByid(id);

        return  CommonUtils.buildResponseEntity(new ApiSuccess(HttpStatus.OK,listOfActiveUserSkillXref));

    }
    @DeleteMapping("/private/logout/token")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);
            if(jwtUtil.isTokenBlacklisted(jwt))
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token is invalidated.");
            String userDetails = jwtUtil.getUsernameFromToken(jwt);
            // Invalidate the token
            jwtUtil.invalidateToken(jwt);
            // Clear user details from session
            request.getSession().removeAttribute("userDetails");
            return ResponseEntity.ok("Successfully logged out.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token.");
        }
    }

    @GetMapping("/private/allskills")
    public ResponseEntity<Object> findAllSkills(
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "size", required = false, defaultValue ="1") Integer pageSize
    )throws PWSException {
        Page<Skill> skilllist = employeeService.getAllSkills(pageNumber,pageSize);
        return CommonUtils.buildResponseEntity(new ApiSuccess(HttpStatus.OK, skilllist));
    }
}
