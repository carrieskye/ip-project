package ip.web.controller;

import ip.service.ExamService;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/Exam")
public class ExamController {
    private ExamService service;

    public ExamController(@Autowired ExamService service) {
        this.service = service;
    }
}
