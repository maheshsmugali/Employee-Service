package com.pws.employees.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author Maes M
 * @Date 09/02/23
 */

@Schema(description = "SignUp Request")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpDto {

    private int id;

    private String firstName;

    private String lastName;

    private Date dob;

    private String email;

    private long phoneNumber;

    private String password;

    private Boolean isActive;

    private String roleName;
}

