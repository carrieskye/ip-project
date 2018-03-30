package ip.web.controller;

import ip.domain.Student;
import ip.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

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

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@Valid Student student, BindingResult result) {
        if (result.hasErrors()) {
            return "student/studentForm";
        }
        if (student.getId() == 0) {
            service.add(student);
        } else {
            service.update(student);
        }
        return "redirect:/student.htm";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView getEditForm(@PathVariable long id) {
        return new ModelAndView("student/studentForm", "student", service.get(id));
    }

    @RequestMapping(value = "/confirmRemoval{id}", method = RequestMethod.GET)
    public ModelAndView getRemoveConfirmation(@PathVariable long id) {
        return new ModelAndView("student/removeStudent", "student", service.get(id));
    }

    @RequestMapping(value = "/remove{id}", method = RequestMethod.GET)
    public String remove(@PathVariable long id) {
        service.delete(id);
        return "redirect:/student.htm";
    }
}
