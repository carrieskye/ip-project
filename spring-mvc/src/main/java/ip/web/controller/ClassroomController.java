package ip.web.controller;

import ip.service.ClassroomService;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/Classroom")
public class ClassroomController {
    private ClassroomService service;

    public ClassroomController(@Autowired ClassroomService service) {
        this.service = service;
    }
}
