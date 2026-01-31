package com.mcmanuel.School;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.sqm.EntityTypeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeoutException;

@Service
@RequiredArgsConstructor
public class SchoolService {
    private static final Logger log = LoggerFactory.getLogger(SchoolService.class);
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
    @TimeLimiter(name = "student", fallbackMethod = "timeOutFallBack")
    @Retry(name = "student" /*,fallbackMethod = "retryFallBack"*/)
    public CompletableFuture<FullResponse> getSchoolWithStudent(int schoolId) {

        return CompletableFuture.supplyAsync(()-> {
            School school =schoolRepo.findById(schoolId).orElseThrow(()-> new EntityNotFoundException("School not found"));
            List<Student> students = client.findAllStudentsBySchoolId(school.getSchoolId());

            return FullResponse.builder()
                            .schoolName(school.getSchoolName())
                            .totalStudent(students.size())
                            .studentList(students)
                            .build();
        });
    }

    public CompletableFuture<FullResponse> timeOutFallBack(int schoolId, TimeoutException ex){
        log.error("Request timed out for school: {}", schoolId);
        return CompletableFuture.supplyAsync(()-> FullResponse.builder()
                .schoolName("Service too slow")
                .build()
        );
    }

    public CompletableFuture<FullResponse> fallBackMethod(int schoolId, RuntimeException ex){
        log.error("General error for school {}: {}", schoolId, ex.getMessage());
        return CompletableFuture.supplyAsync(()-> FullResponse.builder()
                .schoolName("Service Unavailable")
                .build()
        );
    }

}
