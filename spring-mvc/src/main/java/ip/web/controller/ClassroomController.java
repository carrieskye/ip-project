package ip.web.controller;

import ip.domain.Classroom;
import ip.service.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
        ModelAndView modelAndView = new ModelAndView("classroom/classroomForm", "classroom", new Classroom());
        modelAndView.addObject("types", Classroom.allTypes);
        modelAndView.addObject("action", "Add");
        return modelAndView;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView save(@Valid Classroom classroom, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView("classroom/classroomForm", "classroom", classroom);
        modelAndView.addObject("types", Classroom.allTypes);
        modelAndView.addObject("action", classroom.getId() == 0 ? "Add" : "Update");
        if (result.hasErrors()) {
            return modelAndView;
        }
        if (classroom.getId() == 0) {
            if (service.alreadyExists(classroom)) {
                result.rejectValue("id", "error.id", "This classroom already exists.");
                return modelAndView;
            }
            service.add(classroom);
        } else {
            service.update(classroom);
        }
        return new ModelAndView("redirect:/classroom.htm");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView getEditForm(@PathVariable long id) {
        ModelAndView modelAndView = new ModelAndView("classroom/classroomForm", "classroom", service.get(id));
        modelAndView.addObject("types", Classroom.allTypes);
        modelAndView.addObject("action", "Update");
        return modelAndView;
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
