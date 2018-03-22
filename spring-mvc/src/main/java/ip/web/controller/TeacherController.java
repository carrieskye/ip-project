package ip.web.controller;

import ip.domain.Course;
import ip.domain.Teacher;
import ip.service.TeacherService;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/teacher")
public class TeacherController {
    private TeacherService service;

    public TeacherController(@Autowired TeacherService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getTeachers() {
        return new ModelAndView("teacher/overview", "teachers", service.getAll());
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public ModelAndView getNewForm() {
        return new ModelAndView("teacher/teacherForm", "teacher", new Teacher());
    }

    @RequestMapping(method = RequestMethod.POST)
    public String save(Teacher teacher, BindingResult result) {
        if (result.hasErrors()) {
            return "teacher/teacherForm";
        }
        service.add(teacher);
        return "redirect:/teacher.htm";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView getEditForm(@PathVariable String id) {
        return new ModelAndView("teacher/teacherForm", "teacher", service.get(id));
    }
}
