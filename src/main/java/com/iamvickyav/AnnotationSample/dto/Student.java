package com.iamvickyav.AnnotationSample.dto;

import com.iamvickyav.AnnotationSample.annotation.SensitiveField;

import javax.validation.constraints.NotNull;

public class Student {

    @NotNull
    private Integer id;

    @SensitiveField
    @NotNull
    private String name;

    private Integer age;
    private String city;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
