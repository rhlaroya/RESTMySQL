package com.cognixia.jump.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognixia.jump.springboot.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{

	 List <Student> findByFirstName(String firstName);
	 
	 List <Student> findByLastName(String lastName);
	 
	 List <Student> findByEmail(String email);
	 
	 List <Student> findByDepartment(String email);
	 
	 List <Student> findByFirstNameAndLastName(String firstName, String LastName);
	 
	 
}
