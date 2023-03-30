package com.pws.employees.repository;

import com.pws.employees.entity.Skill;
import com.pws.employees.entity.User;
import com.pws.employees.entity.UserSkillXref;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserSkillXrefRepository extends JpaRepository<UserSkillXref,Integer> {

@Query("select o.skill from  UserSkillXref o where o.skill.id= :id")
   List<Skill> fetchuserSkillsByid(Integer id);
}
