package ua.nure.library.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Main page controller
 */
@RestController
@RequestMapping("/main")
public class MainPageRest {

    /**
     *
     * @return hello
     */
    @GetMapping("/")
    public String printWelcome() {
        return "hello";
    }
}
