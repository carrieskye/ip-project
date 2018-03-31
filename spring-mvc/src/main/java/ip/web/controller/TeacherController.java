package ip.web.controller;

import ip.domain.Teacher;
import ip.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

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

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@Valid Teacher teacher, BindingResult result) {
        if (result.hasErrors()) {
            return "teacher/teacherForm";
        }
        if (service.alreadyExists(teacher)) {
            result.rejectValue("id", "error.id", "This teacher already exists.");
            return "teacher/teacherForm";
        }

        if (teacher.getId() == 0) {
            service.add(teacher);
        } else {
            service.update(teacher);
        }
        return "redirect:/teacher.htm";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView getEditForm(@PathVariable long id) {
        return new ModelAndView("teacher/teacherForm", "teacher", service.get(id));
    }

    @RequestMapping(value = "/confirmRemoval{id}", method = RequestMethod.GET)
    public ModelAndView getRemoveConfirmation(@PathVariable long id) {
        Teacher teacher = service.get(id);
        if (teacher.getCourses() > 0) {
            ModelAndView modelAndView = new ModelAndView("teacher/removeTeacherError", "teacher", teacher);
            String errorMessageOne = teacher.getInfo() + " still has 1 ongoing course. This course needs to be removed before " + teacher.getFirstName() + " can be removed.";
            String errorMessageMany = teacher.getInfo() + "still has " + teacher.getCourses() + " ongoing courses. These need to be removed before" + teacher.getFirstName() + " can be removed.";
            modelAndView.addObject("courseError", teacher.getCourses() <= 1 ? errorMessageOne: errorMessageMany);
            return modelAndView;
        }
        return new ModelAndView("teacher/removeTeacher", "teacher", teacher);
    }

    @RequestMapping(value = "/remove{id}", method = RequestMethod.GET)
    public String remove(@PathVariable long id) {
        service.delete(id);
        return "redirect:/teacher.htm";
    }
}
