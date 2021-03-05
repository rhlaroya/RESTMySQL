package com.cognixia.jump.springboot.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.springboot.model.Student;
import com.cognixia.jump.springboot.repository.StudentRepository;

@RequestMapping("/api")
@RestController
public class StudentController {

	@Autowired
	StudentRepository repo;
	
	@GetMapping("/students")
	public List<Student> getAllStudents() {
		return repo.findAll();
	}
	
	@GetMapping("/students/{id}")
	public Student getStudent(@PathVariable long id) {
		Optional<Student> optStudent = repo.findById(id);
		
		if(optStudent.isPresent()) { 
			return optStudent.get();
		}
		
		return new Student();
		
	}
	
	// do additional search / get mapping here
	@GetMapping("/students/firstname/{firstname}")
	public List<Student> getStudentsByFirstName(@PathVariable String firstname) {
		return repo.findByFirstName(firstname);
	}
	
	@GetMapping("/students/lastname/{lastname}")
	public List<Student> getStudentsByLastName(@PathVariable String lastname) {
		return repo.findByLastName(lastname);
	}
	
	@GetMapping("/students/email/{email}")
	public List<Student> getStudentsByEmail(@PathVariable String email) {
		return repo.findByEmail(email);
	}
	
	@GetMapping("/students/department/{department}")
	public List<Student> getStudentsByDepartment(@PathVariable String department) {
		return repo.findByDepartment(department);
	}

	
	@PostMapping("/add/student")
	public String addStudent(@RequestBody Student newStudent) {
		Student added = repo.save(newStudent);
		
		System.out.println("Added" + added);
		return added.toString();
	}
	
	@PutMapping("/update/student")
	public @ResponseBody String updateStudent(@RequestBody Student student) {
		
		Optional<Student> found = repo.findById(student.getId());
		
		if(found.isPresent()) {
			repo.save(student);
			return "Saved: " + student.toString();
		}
		else {
			return "Could not updated student with id = " + student.getId();
		}
		
	}
	
	// patch mapping here - (partial update)
	@PatchMapping("update/student/department")
	public @ResponseBody String updateStudentDepartment(@RequestBody Map<String, String> deptUpdate) {
		
		long id = Long.parseLong(deptUpdate.get("id"));
		String department = deptUpdate.get("department");
		
		Optional<Student> found = repo.findById(id);
		
		if(found.isPresent()) {
			Student toUpdate = found.get();
			
			String old = toUpdate.getDepartment();
			
			toUpdate.setDepartment(department);
			
			repo.save(toUpdate);
			
			return "Old department: " + old + "\nNewDepartment: " + department;
		}
		else {
			return "Could not update the department for student with id = " + id;
		}
		
	}
	
	//patch email
	@PatchMapping("/update/student/email")
	public @ResponseBody String updateStudentEmail(@RequestBody Map<String, String> emailUpdate) {
		
		long id = Long.parseLong(emailUpdate.get("id"));
		String email = emailUpdate.get("email");
		
		Optional<Student>found = repo.findById(id);
		
		if(found.isPresent()) {
			Student toUpdate = found.get();
			toUpdate.setEmail(email);
			repo.save(toUpdate);
			return email;
		}
		else {
			return "Could not locate student with id = " + id;
		}
	}
	
	//patch firstName
	@PatchMapping("/update/student/firstname")
	public @ResponseBody String updateStudentfirstName(@RequestBody Map<String, String> firstNameUpdate) {
		
		long id = Long.parseLong(firstNameUpdate.get("id"));
		String firstName = firstNameUpdate.get("firstName");
		
		Optional<Student>found = repo.findById(id);
		
		if(found.isPresent()) {
			Student toUpdate = found.get();
			toUpdate.setFirstName(firstName);
			repo.save(toUpdate);
			return firstName;
		}
		else {
			return "Could not locate student with id = " + id;
		}
	}
	
	//patch lastName
		@PatchMapping("/update/student/lastname")
		public @ResponseBody String updateStudentLastName(@RequestBody Map<String, String> lastNameUpdate) {
			
			long id = Long.parseLong(lastNameUpdate.get("id"));
			String lastName = lastNameUpdate.get("lastName");
			
			Optional<Student>found = repo.findById(id);
			
			if(found.isPresent()) {
				Student toUpdate = found.get();
				toUpdate.setLastName(lastName);
				repo.save(toUpdate);
				return lastName;
			}
			else {
				return "Could not locate student with id = " + id;
			}
		}
		
	@DeleteMapping("/delete/student/{id}")
	public ResponseEntity<String> deleteStudent(@PathVariable long id) {
		
		Optional<Student> found = repo.findById(id);
		
		if(found.isPresent()) {
			repo.deleteById(id);
			return ResponseEntity.status(200).body("Deleted student with id = " + id);
		}
		else {
			return ResponseEntity.status(400).body("Student with id = " + id + " not found.");
		}
		
	}
}
