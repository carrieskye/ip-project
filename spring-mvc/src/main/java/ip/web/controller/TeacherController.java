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

    @RequestMapping(value="sortedBy{column}", method = RequestMethod.GET)
    public ModelAndView getTeachersSorted(@PathVariable String column) {
        return new ModelAndView("teacher/overview", "teachers", service.getAllSorted(column.substring(0, 1).toLowerCase() + column.substring(1)));
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public ModelAndView getNewForm() {
        ModelAndView modelAndView = new ModelAndView("teacher/teacherForm", "teacher", new Teacher());
        modelAndView.addObject("action", "Add");
        return modelAndView;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView save(@Valid Teacher teacher, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView("teacher/teacherForm");
        modelAndView.addObject("action", teacher.getId() == 0 ? "Add" : "Update");
        if (result.hasErrors()) {
            return modelAndView;
        }
        if (teacher.getId() == 0) {
            if (service.alreadyExists(teacher)) {
                result.rejectValue("id", "error.id", "This teacher already exists.");
                return modelAndView;
            }
            service.add(teacher);
        } else {
            service.update(teacher);
        }
        return new ModelAndView("redirect:/teacher.htm");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView getEditForm(@PathVariable long id) {
        ModelAndView modelAndView = new ModelAndView("teacher/teacherForm", "teacher", service.get(id));
        modelAndView.addObject("action", "Update");
        return modelAndView;
    }

    @RequestMapping(value = "/confirmRemoval{id}", method = RequestMethod.GET)
    public ModelAndView getRemoveConfirmation(@PathVariable long id) {
        Teacher teacher = service.get(id);
        if (teacher.getCourses() > 0) {
            ModelAndView modelAndView = new ModelAndView("teacher/removeTeacherError", "teacher", teacher);
            String errorMessageOne = teacher.getInfo() + " still has 1 ongoing course. This course needs to be removed before " + teacher.getFirstName() + " can be removed.";
            String errorMessageMany = teacher.getInfo() + " still has " + teacher.getCourses() + " ongoing courses. These need to be removed before " + teacher.getFirstName() + " can be removed.";
            modelAndView.addObject("courseError", teacher.getCourses() <= 1 ? errorMessageOne : errorMessageMany);
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
