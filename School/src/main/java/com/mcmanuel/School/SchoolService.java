package com.mcmanuel.School;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.sqm.EntityTypeException;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class SchoolService {
    private final SchoolRepository schoolRepo;
    private final StudentClient client;

    public School saveSchool(School student)throws EntityTypeException {
        return schoolRepo.save(student);
    }

    public List<School> getAllSchool(){
        return schoolRepo.findAll();
    }

    public School getSchoolById(int schoolId) {
        System.out.println("in the service method");
        return schoolRepo.findById(schoolId).orElseThrow(EntityNotFoundException::new);
    }


    @CircuitBreaker(name = "student", fallbackMethod = "fallBackMethod")
    @TimeLimiter(name = "student")
    public CompletableFuture<FullResponse> getSchoolWithStudent(int schoolId) {
        School school =schoolRepo.findById(schoolId).orElseThrow(()-> new EntityNotFoundException("School not found"));

        List<Student> students = client.findAllStudentsBySchoolId(school.getSchoolId());

        return CompletableFuture.supplyAsync(()-> FullResponse.builder()
                .schoolName(school.getSchoolName())
                .totalStudent(students.size())
                .studentList(students)
                .build()
        );
    }

    public CompletableFuture<FullResponse> fallBackMethod(int schoolId, RuntimeException ex){
        return CompletableFuture.supplyAsync(()-> FullResponse.builder()
                .schoolName("Service Unavailable")
                .totalStudent(0)
                .studentList(List.of())
                .build()
        );
    }

}
