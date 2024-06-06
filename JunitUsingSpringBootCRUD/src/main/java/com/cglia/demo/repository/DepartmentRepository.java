package com.cglia.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cglia.demo.entity.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

}
