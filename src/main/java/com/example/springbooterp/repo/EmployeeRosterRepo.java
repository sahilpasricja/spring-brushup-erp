package com.example.springbooterp.repo;

import com.example.springbooterp.dao.EmployeeRoster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRosterRepo extends JpaRepository<EmployeeRoster,Long> {

}
