package ip.web.controller;

import ip.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/Classroom")
public class StudentController {
    private StudentService service;

    public StudentController(@Autowired StudentService service) {
        this.service = service;
    }
}
