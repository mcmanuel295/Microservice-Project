package com.mcmanuel.School;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FullResponse {
    private String schoolName;
    private String email;
    private int noOfStudents;
    private List<Student> students;
}
