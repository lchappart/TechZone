package com.techzone.controller.web;

import com.techzone.dto.AuthResponse;
import com.techzone.dto.LoginRequest;
import com.techzone.dto.RegisterRequest;
import com.techzone.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping
public class AuthWebController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("title", "Connexion - TechZone");
        model.addAttribute("loginRequest", new LoginRequest());
        return "auth/login";
    }
    
    @PostMapping("/login")
    public String login(@ModelAttribute LoginRequest loginRequest, 
                       HttpServletResponse response,
                       RedirectAttributes redirectAttributes) {
        try {
            AuthResponse authResponse = userService.login(loginRequest);
            
            Cookie tokenCookie = new Cookie("jwt_token", authResponse.getToken());
            tokenCookie.setPath("/");
            tokenCookie.setMaxAge(30 * 60);
            tokenCookie.setHttpOnly(true);
            response.addCookie(tokenCookie);
            
            boolean isAdmin = authResponse.getRoles() != null && authResponse.getRoles().stream()
                    .anyMatch(r -> "ADMIN".equalsIgnoreCase(r) || "ROLE_ADMIN".equalsIgnoreCase(r));
            if (isAdmin) {
                return "redirect:/admin/dashboard";
            } else {
                return "redirect:/products";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Email ou mot de passe incorrect");
            return "redirect:/login";
        }
    }
    
    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("title", "Inscription - TechZone");
        model.addAttribute("registerRequest", new RegisterRequest());
        return "auth/register";
    }
    
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("registerRequest") RegisterRequest registerRequest,
                          BindingResult bindingResult,
                          HttpServletResponse response,
                          Model model,
                          RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Inscription - TechZone");
            return "auth/register";
        }
        try {
            AuthResponse authResponse = userService.register(registerRequest);
            
            Cookie tokenCookie = new Cookie("jwt_token", authResponse.getToken());
            tokenCookie.setPath("/");
            tokenCookie.setMaxAge(30 * 60);
            tokenCookie.setHttpOnly(true);
            response.addCookie(tokenCookie);
            
            redirectAttributes.addFlashAttribute("message", "Inscription réussie ! Bienvenue sur TechZone.");
            return "redirect:/products";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/register";
        }
    }
    
    @GetMapping("/logout")
    public String logoutGet(HttpServletResponse response) {
        clearJwtCookie(response);
        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logoutPost(HttpServletResponse response) {
        clearJwtCookie(response);
        return "redirect:/";
    }

    private void clearJwtCookie(HttpServletResponse response) {
        Cookie tokenCookie = new Cookie("jwt_token", "");
        tokenCookie.setPath("/");
        tokenCookie.setMaxAge(0);
        tokenCookie.setHttpOnly(true);
        response.addCookie(tokenCookie);
    }
}
