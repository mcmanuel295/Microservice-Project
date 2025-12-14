package com.mcmanuel.School;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class FullResponse {
    private String schoolName;
    private int totalStudent;
    private List<Student> studentList;
}
