package com.mcmanuel.Student;

import lombok.RequiredArgsConstructor;
import org.hibernate.query.sqm.EntityTypeException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepo;

    public Student saveStudent(Student student)throws EntityTypeException {
        return studentRepo.save(student);
    }

    public List<Student> findAllStudents(){
        return studentRepo.findAll();
    }

    public List<Student> findStudentsBySchool(int schoolId) {
        System.out.println("in the service method");
        return studentRepo.findAllBySchoolId(schoolId);
    }
}
