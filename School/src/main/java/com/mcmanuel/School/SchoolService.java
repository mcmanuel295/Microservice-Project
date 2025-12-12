package com.mcmanuel.School;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.sqm.EntityTypeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SchoolService {
    private final SchoolRepository schoolRepo;
    private final StudentClient client;


    public School saveSschool(School school)throws EntityTypeException {
        return schoolRepo.save(school);
    }

    public List<School> findAllSchool(){
        return schoolRepo.findAll();
    }

    public FullResponse findSchoolWithStudent(int schoolId) {
        School school = schoolRepo.findById(schoolId)
                .orElseThrow(EntityNotFoundException::new);

        List<Student> stud =client.findStudentsBySchool(schoolId);


        assert stud != null;
        return FullResponse.builder()
                .schoolName(school.getSchoolName())
                .email(school.getEmail())
                .noOfStudents(stud.size())
                .students(stud)
                .build();

    }

    public School getSchoolById(int schoolId) {
        return schoolRepo.findById(schoolId).orElseThrow(()-> new EntityNotFoundException("Entity Not Found"));
    }
}
