package com.pws.employees.dto;


import com.pws.employees.entity.UserSkillXref.Keyword;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSkillXrefDTO {
    @Schema(description = "Enter Id", example = "111", required = true)
    private Integer id;

    private Integer userId;

    private Integer skillId;

    private Keyword skillLevel;
    @Schema(description = "Enter IsActive",example = "true/false",required = true)
    private Boolean isActive;
}
