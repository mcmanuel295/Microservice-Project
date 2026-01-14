package com.mcmanuel.School;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.sqm.EntityTypeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schools")
@Slf4j
public class SchoolController {
    private final SchoolService service;

    @PostMapping("/")
    public ResponseEntity<School> addSchool(@RequestBody School school){
        School savedStudent;
        try{
            savedStudent =service.saveSchool(school);
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

    @GetMapping("/{schoolId}")
    public ResponseEntity<School> getSchoolById(@PathVariable Integer schoolId){
        School savedStudent;
        try{
            savedStudent =service.getSchoolById(schoolId);
            return new ResponseEntity<>(savedStudent,HttpStatus.CREATED);
        }
        catch (EntityNotFoundException ex){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        catch (Exception ex){
            log.error(ex.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<School>> findAllStudents(){
        return new ResponseEntity<>( service.getAllSchool(),HttpStatus.OK);
    }


    @GetMapping("/{schoolId}/with-students")
    public ResponseEntity<FullResponse> findSchoolWithStudents(@PathVariable int schoolId){
        try{
            return new ResponseEntity<>( service.getSchoolWithStudent(schoolId),HttpStatus.OK);
        }
        catch (EntityNotFoundException ex){
            return new ResponseEntity<>(new FullResponse (
                "Invalid ID",0,null),HttpStatus.BAD_REQUEST);
        }
        catch (Exception ex){
            log.error(ex.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
