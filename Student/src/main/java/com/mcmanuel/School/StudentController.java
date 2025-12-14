package com.mcmanuel.Student;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.sqm.EntityTypeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/students")
@Slf4j
public class StudentController {
    private final StudentService service;

    @PostMapping("/")
    public ResponseEntity<Student> addStudent(@RequestBody Student student){
        Student savedStudent;
        try{
            savedStudent =service.saveStudent(student);
            return new ResponseEntity<>(savedStudent,HttpStatus.CREATED);
        }
        catch (EntityTypeException ex){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        catch (Exception ex){
            log.error(ex.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<Student>> findAllStudents(){
        return new ResponseEntity<>( service.findAllStudents(),HttpStatus.OK);
    }

    @GetMapping("/{schoolId}")
    public ResponseEntity<List<Student>> findStudentsBySchool(@PathVariable(name = "schoolId") int schoolId){
        System.out.println("in the controller method");
        return new ResponseEntity<>( service.findStudentsBySchool(schoolId),HttpStatus.OK);
    }
}
