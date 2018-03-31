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
        if (service.alreadyExists(classroom)) {
            result.rejectValue("id", "error.id", "This classroom already exists.");
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

    @RequestMapping(value = "/confirmRemoval{id}", method = RequestMethod.GET)
    public ModelAndView getRemoveConfirmation(@PathVariable long id) {
        Classroom classroom = service.get(id);
        if (classroom.getExams() > 0) {
            ModelAndView modelAndView = new ModelAndView("classroom/removeClassroomError", "classroom", classroom);
            String errorMessageOne = "There is 1 exam that takes place in classroom " + classroom.getInfo() + ". This exam needs to be removed before classroom " + classroom.getLocation() + " can be removed.";
            String errorMessageMany = "There are " + classroom.getExams() + " exams that takes place in classroom " + classroom.getInfo() + ". They need to be removed before classroom " + classroom.getLocation() + " can be removed.";
            modelAndView.addObject("examError", classroom.getExams() <= 1 ? errorMessageOne : errorMessageMany);
            return modelAndView;
        }
        return new ModelAndView("classroom/removeClassroom", "classroom", service.get(id));
    }


    @RequestMapping(value = "/remove{id}", method = RequestMethod.GET)
    public String remove(@PathVariable long id) {
        service.delete(id);
        return "redirect:/classroom.htm";
    }
}
