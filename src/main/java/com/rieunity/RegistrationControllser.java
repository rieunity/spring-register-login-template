package com.rieunity;

import com.rieunity.persistence.dao.UserRepository;
import com.rieunity.persistence.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/")
public class RegistrationControllser {


    private UserRepository userRepository;

    @Autowired
    public  RegistrationControllser(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String welcomePage() {
        return "welcome";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String showRegistration(Model model) {
        model.addAttribute(new User());
        return "registration";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registrationProcessing(@Valid User user, Errors errors, RedirectAttributes model) {
        if (errors.hasErrors()) {
            return "registration";
        }
        String encodedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
        model.addAttribute("username", user.getUsername());
        model.addFlashAttribute("user", user);
        return "redirect:/";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLogin() {
        return "login";
    }

    @RequestMapping(value = "/submit", method = RequestMethod.GET)
    public String showSubmit() {
        return "submit";
    }
}
