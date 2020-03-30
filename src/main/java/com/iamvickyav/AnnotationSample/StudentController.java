package com.iamvickyav.AnnotationSample;

import com.iamvickyav.AnnotationSample.dto.Staff;
import com.iamvickyav.AnnotationSample.dto.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class StudentController {

    Logger LOGGER = LoggerFactory.getLogger(StudentController.class);

    @RequestMapping(value = "/student", method = RequestMethod.POST)
    Student saveStudent(@RequestBody @Valid Student student) {
        LOGGER.info("Student object received in controller for student: name={}", student.getName());
        return student;
    }

    @RequestMapping(value = "/staff", method = RequestMethod.POST)
    String saveStudent(@RequestBody Staff staff) {
        LOGGER.info("Staff object saved successfully in DB");
        return staff.getName();
    }
}
