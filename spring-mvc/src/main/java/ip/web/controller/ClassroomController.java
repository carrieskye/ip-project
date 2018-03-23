package ip.web.controller;

import ip.domain.Classroom;
import ip.service.ClassroomService;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

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

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@Valid Classroom classroom, BindingResult result) {
        if (result.hasErrors()) {
            return "classroom/classroomForm";
        }
        if (classroom.getId() == 0) {
            service.add(classroom);
        } else {
            service.update(classroom);
        }
        return "redirect:/classroom.htm";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView getEditForm(@PathVariable long id) {
        return new ModelAndView("classroom/classroomForm", "classroom", service.get(id));
    }
}
