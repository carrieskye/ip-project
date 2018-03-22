package ip.web.controller;

import ip.domain.Course;
import ip.domain.Student;
import ip.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/student")
public class StudentController {
    private StudentService service;

    public StudentController(@Autowired StudentService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getStudents() {
        return new ModelAndView("student/overview", "students", service.getAll());
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public ModelAndView getNewForm() {
        return new ModelAndView("student/studentForm", "student", new Student());
    }

    @RequestMapping(method = RequestMethod.POST)
    public String save(Student student, BindingResult result) {
        if (result.hasErrors()) {
            return "student/studentForm";
        }
        service.add(student);
        return "redirect:/student.htm";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView getEditForm(@PathVariable String id) {
        return new ModelAndView("student/studentForm", "student", service.get(id));
    }
}