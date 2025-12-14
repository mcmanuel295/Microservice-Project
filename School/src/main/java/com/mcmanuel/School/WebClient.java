package com.mcmanuel.School;

import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;

@FeignClient(name = "student-service", url = "${spring.application.config.import}")
public interface WebClient {

     List<Student> findAllStudentsBySchoolId(Integer schoolId);
}
