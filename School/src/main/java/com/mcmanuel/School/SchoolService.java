package com.mcmanuel.School;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.sqm.EntityTypeException;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public FullResponse getSchoolWithStudent(int schoolId) {
        System.out.println("in the service method");
        School school =schoolRepo.findById(schoolId).orElseThrow(EntityNotFoundException::new);

        List<Student> students = client.findAllStudentsBySchoolId(school.getSchoolId());

        return FullResponse.builder()
                .schoolName(school.getSchoolName())
                .totalStudent(students.size())
                .studentList(students)
                .build();
    }
}
