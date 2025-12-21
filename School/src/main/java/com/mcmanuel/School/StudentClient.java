package com.mcmanuel.School;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "student-service")
public interface StudentClient {
     @GetMapping("/{schoolId}")
     List<Student> findAllStudentsBySchoolId(Integer schoolId);
}
