package com.mcmanuel.School;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Student {

    private String firstname;
    private String lastname;
    private String email;
    private Integer schoolId;
}
