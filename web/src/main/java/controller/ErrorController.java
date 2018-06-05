package controller;

import exception.ErrorMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorController {

    @GetMapping("/error/{message}")
    public ModelAndView showErrorPage(@PathVariable String message) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("message", message);
        return modelAndView;
    }
}
