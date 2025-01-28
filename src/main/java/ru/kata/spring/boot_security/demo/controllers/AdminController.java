package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.service.RoleService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminController(UserService userService,
                           RoleService roleService,
                           PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/users")
    public String getUsersList(Model model, Principal principal) {
        Optional<User> currentUserOpt = userService.findByEmail(principal.getName());
        User currentUser = currentUserOpt.get();
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("users", userService.findAll());
        model.addAttribute("listRoles", roleService.findAll());
        return "admin_page";
    }

    @GetMapping("/users/new")
    public String createUserForm(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("listRoles", roleService.findAll());

        return "add_user";
    }

    @PostMapping("/users")
    public String createUser(@ModelAttribute("user") @Valid User user,
                             BindingResult bindingResult, Model model) {
        Optional<User> userByEmail = userService.findByEmail(user.getEmail());
        if (userByEmail.isPresent()) {
            bindingResult.rejectValue("email", "error.email",
                    "This email is already in use");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("listRoles", roleService.findAll());
            return "add_user";
        }

        this.userService.save(user);
        return "redirect:/admin/users/";
    }

    @PatchMapping("/users/edit")
    public String editUser(@ModelAttribute("user") @Valid User updatedUser,
                           BindingResult bindingResult, Model model) {
        Optional<User> existingUserOpt = userService.findById(updatedUser.getId());

        if (existingUserOpt.isEmpty()) {
            bindingResult.rejectValue("id", "error.id", "User not found");
        } else {
            User existingUser = existingUserOpt.get();

            Optional<User> userByEmail = userService.findByEmail(updatedUser.getEmail());
            if (userByEmail.isPresent() && !userByEmail.get().getId().equals(updatedUser.getId())) {
                bindingResult.rejectValue("email", "error.email", "This email is already in use");
            }

            if (bindingResult.hasErrors()) {
                model.addAttribute("listRoles", roleService.findAll());
                return "admin_page";
            }

            if (updatedUser.getRoles() == null || updatedUser.getRoles().isEmpty()) {
                updatedUser.setRoles(existingUser.getRoles());
            }

            userService.updateUser(updatedUser);
        }

        return "redirect:/admin/users";
    }

    @DeleteMapping("/users/delete")
    public String deleteUser(@RequestParam("id") Long id) {
        userService.deleteById(id);
        return "redirect:/admin/users";
    }
}