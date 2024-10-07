package com.application.controller;

import com.application.exception.DataNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler
    public ModelAndView handleDataNotFoundException(DataNotFoundException e) {
        return new ModelAndView("exception_page", "message", e.getMessage());
    }
}
