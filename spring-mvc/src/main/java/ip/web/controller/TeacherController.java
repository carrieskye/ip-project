package ip.web.controller;

import ip.service.TeacherService;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/Classroom")
public class TeacherController {
    private TeacherService service;

    public TeacherController(@Autowired TeacherService service) {
        this.service = service;
    }
}
