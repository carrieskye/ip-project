package ip.web.controller;

import ip.domain.Classroom;
import ip.domain.Course;
import ip.service.ClassroomService;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/classroom")
public class ClassroomController {
    private ClassroomService service;

    public ClassroomController(@Autowired ClassroomService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getClassrooms() {
        return new ModelAndView("classroom/overview", "classrooms", service.getAll());
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public ModelAndView getNewForm() {
        return new ModelAndView("classroom/classroomForm", "classroom", new Classroom());
    }

    @RequestMapping(method = RequestMethod.POST)
    public String save(Classroom classroom, BindingResult result) {
        if (result.hasErrors()) {
            return "classroom/classroomForm";
        }
        service.add(classroom);
        return "redirect:/classroom.htm";
    }

    @RequestMapping(value = "/{location}", method = RequestMethod.GET)
    public ModelAndView getEditForm(@PathVariable String location) {
        return new ModelAndView("classroom/classroomForm", "classroom", service.get(location));
    }
}
