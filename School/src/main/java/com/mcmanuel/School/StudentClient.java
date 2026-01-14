package com.mcmanuel.School;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "student-service",url = "http://localhost:8082/students")
public interface StudentClient {
     @GetMapping("/{schoolId}")
     List<Student> findAllStudentsBySchoolId(@PathVariable Integer schoolId);
}
