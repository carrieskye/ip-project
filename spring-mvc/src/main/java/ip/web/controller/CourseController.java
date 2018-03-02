package ip.web.controller;

import ip.service.CourseService;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/Course")
public class CourseController {
    private CourseService service;

    public CourseController(@Autowired CourseService service) {
        this.service = service;
    }


}
