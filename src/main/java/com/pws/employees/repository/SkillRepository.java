package com.pws.employees.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pws.employees.entity.Skill;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Integer> {
}