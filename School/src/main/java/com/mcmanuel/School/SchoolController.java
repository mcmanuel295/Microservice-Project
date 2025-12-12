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
    public ResponseEntity<School> addStudent(@RequestBody School student){
        try{
            return new ResponseEntity<>(service.saveSschool(student),HttpStatus.CREATED);
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
    public ResponseEntity<List<School>> findAllStudents(){
        return new ResponseEntity<>( service.findAllSchool(),HttpStatus.OK);
    }

    @GetMapping("/{schoolId}/with-students")
    public ResponseEntity<FullResponse> findAllStudents(@PathVariable Integer schoolId){
        try{
            return new ResponseEntity<>( service.findSchoolWithStudent(schoolId),HttpStatus.OK);
        }
        catch (Exception ex){
            log.error(ex.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/{schoolId}")
    public ResponseEntity<School> getSchoolById(@PathVariable int schoolId){
        try{
            return new ResponseEntity<>( service.getSchoolById(schoolId),HttpStatus.OK);
        }
        catch (EntityNotFoundException ex){
            log.warn(ex.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        catch (Exception ex){
            log.error(ex.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
}
