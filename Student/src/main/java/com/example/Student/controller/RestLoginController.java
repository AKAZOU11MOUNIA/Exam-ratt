package com.example.Student.controller;

import com.example.Student.model.LoginRequest;
import com.example.Student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/students/login")
public class RestLoginController {

    @Autowired
    private StudentService studentService;

    @PostMapping
    public String login(@RequestBody LoginRequest loginRequest, Model model) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        // Vérifier les informations d'identification
        boolean isAuthenticated = studentService.login(username, password);

        if (isAuthenticated) {
            // Si authentifié, redirigez vers la page de détails de l'étudiant
            model.addAttribute("username", username);
            return "redirect:/studentDetails";
        } else {
            // Si l'authentification échoue, retournez à la page de login
            model.addAttribute("error", "Invalid credentials");
            return "login";
        }
    }
}
