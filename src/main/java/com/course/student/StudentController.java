package com.course.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudentController {

    @Autowired
    StudentRepository studentRepo;
    @PostMapping("/addStudent")
    public void addStudent(@RequestBody Student student){
        studentRepo.save(student);
    }
}
