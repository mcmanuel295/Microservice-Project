package com.mcmanuel.School;

import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;

@FeignClient(name = "student-service")
public interface StudentClient {

     List<Student> findAllStudentsBySchoolId(Integer schoolId);
}
