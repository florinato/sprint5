package cat.itacademy.barcelonactiva.quintanacipres.oscar.s05.t01.n01.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "index";
    }
}
