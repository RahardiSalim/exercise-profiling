package com.advpro.profiling.tutorial.service;

import com.advpro.profiling.tutorial.model.Student;
import com.advpro.profiling.tutorial.model.StudentCourse;
import com.advpro.profiling.tutorial.repository.StudentCourseRepository;
import com.advpro.profiling.tutorial.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Comparator;
import java.util.Optional;

/**
 * @author muhammad.khadafi
 */
@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentCourseRepository studentCourseRepository;

    public List<StudentCourse> getAllStudentsWithCourses() {
        List<StudentCourse> studentCourses = studentCourseRepository.findAll(); // Fetch all at once

        // Use a Map to associate students with their courses
        Map<Long, Student> studentMap = studentRepository.findAll()
                .stream()
                .collect(Collectors.toMap(Student::getId, student -> student));

        // Assign students to their courses
        for (StudentCourse studentCourse : studentCourses) {
            studentCourse.setStudent(studentMap.get(studentCourse.getStudent().getId()));
        }

        return studentCourses;
    }


    public Optional<Student> findStudentWithHighestGpa() {
        return studentRepository.findAll().stream()
                .max(Comparator.comparingDouble(Student::getGpa));
    }

    public String joinStudentNames() {
        List<Student> students = studentRepository.findAll();
        return students.stream()
                .map(Student::getName)
                .collect(Collectors.joining(", "));
    }
}

