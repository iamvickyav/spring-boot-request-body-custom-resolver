package com.iamvickyav.AnnotationSample;

import com.iamvickyav.AnnotationSample.dto.Staff;
import com.iamvickyav.AnnotationSample.dto.Student;
import com.iamvickyav.AnnotationSample.security.Encryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class StudentController {

    Logger LOGGER = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    Encryptor encryptor;

    @RequestMapping(value = "/student", method = RequestMethod.POST)
    String saveStudent(@Valid Student student) {
        LOGGER.info("Student object saved successfully in DB");
        return student.getName();
    }

    @RequestMapping(value = "/staff", method = RequestMethod.POST)
    String saveStudent(@RequestBody Staff staff) {
        LOGGER.info("Staff object saved successfully in DB");
        return staff.getName();
    }

    @RequestMapping(value = "/decrypt", method = RequestMethod.POST, consumes = MediaType.TEXT_PLAIN_VALUE)
    String getDecryptedValue(@RequestBody String encryptedString) {
        LOGGER.info("Request received for decypting string");
        return encryptor.decrypt(encryptedString);
    }
}
