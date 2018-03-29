package ip.web.controller;

import ip.domain.Exam;
import ip.service.ExamService;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/exam")
public class ExamController {
    private ExamService service;

    public ExamController(@Autowired ExamService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getExams() {
        return new ModelAndView("exam/overview", "exams", service.getAll());
    }


    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public ModelAndView getNewForm() {
        return new ModelAndView("exam/examForm", "exam", new Exam());
    }

    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public String save(@Valid Exam exam, BindingResult result) {
        if (result.hasErrors()) {
            return "exam/examForm";
        }
        if (exam.getId() == 0) {
            service.add(exam);
        } else {
            service.update(exam);
        }
        return "redirect:/exam.htm";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView getEditForm(@PathVariable long id) {
        return new ModelAndView("exam/examForm", "exam", service.get(id));
    }
}
