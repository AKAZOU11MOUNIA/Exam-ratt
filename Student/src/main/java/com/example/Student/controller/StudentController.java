
package com.example.Student.controller;

import com.example.Student.model.Student;
import com.example.Student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    // Endpoint pour obtenir tous les étudiants
    @GetMapping
    public Iterable<Student> getAllStudents() {
        return studentService.getAll();
    }

    // Endpoint pour obtenir un étudiant par son ID
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable("id") Long id) {
        Optional<Student> student = studentService.getSpecificStudent(id);
        return student.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Endpoint pour ajouter un nouvel étudiant
    @PostMapping
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        Student newStudent = studentService.addNewStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(newStudent);
    }

    // Endpoint pour mettre à jour un étudiant existant
    @PutMapping("/{id}")
    public ResponseEntity<String> updateStudent(@PathVariable("id") Long id, @RequestBody Student student) {
        ResponseEntity<String> response = studentService.updateStudent(id, student);
        return ResponseEntity.status(HttpStatus.OK).body(response.getBody());
    }

    // Endpoint pour supprimer un étudiant par son ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable("id") Long id) {
        ResponseEntity<String> response = studentService.deleteStudent(id);
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }
}
