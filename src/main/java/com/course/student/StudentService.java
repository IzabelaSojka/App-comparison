package com.course.student;

import org.hibernate.annotations.Comment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class StudentService {

    public static List<Student> getStudents(){
        return List.of(
                new Student(
                        1L,
                        "Anna",
                        "anna@gmail.com",
                        LocalDate.of(2000, 9, 15),
                        23

                )
        );
    }
}
