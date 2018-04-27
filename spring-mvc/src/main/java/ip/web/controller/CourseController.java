package ip.web.controller;

import ip.domain.Course;
import ip.service.CourseService;
import ip.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;

@Controller
@RequestMapping(value = "/course")
public class CourseController {
    private CourseService service;
    private TeacherService teacherService;

    public CourseController(@Autowired CourseService service, @Autowired TeacherService teacherService) {
        this.service = service;
        this.teacherService = teacherService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getCourses() {
        ArrayList<Course> courses = new ArrayList<>();
        for (Course course : service.getAll()) {
            course.setAttribute("teacher", teacherService.get(course.getTeacher()));
            courses.add(course);
        }

        return new ModelAndView("course/overview", "courses", courses);
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public ModelAndView getNewForm() {
        ModelAndView modelAndView = new ModelAndView("course/courseForm", "course", new Course());
        modelAndView.addObject("teachers", teacherService.getAll());
        modelAndView.addObject("action","Add");
        return modelAndView;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView save(@Valid Course course, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView("course/courseForm", "course", course);
        modelAndView.addObject("teachers", teacherService.getAll());
        modelAndView.addObject("action", course.getId() == 0 ? "Add" : "Update");
        if (result.hasErrors()) {
            return modelAndView;
        }
        if (course.getId() == 0) {
            if (service.alreadyExists(course)) {
                result.rejectValue("id", "error.id", "This course already exists.");
                return modelAndView;
            }
            service.add(course);
            teacherService.increaseCourses(teacherService.get(course.getTeacher()));
        } else {
            updateTeacher(course);
            service.update(course);
        }
        return new ModelAndView("redirect:/course.htm");
    }

    private void updateTeacher(Course course) {
        long oldTeacher = service.get(course.getId()).getTeacher();
        long newTeacher = course.getTeacher();

        if (oldTeacher != newTeacher) {
            teacherService.decreaseCourses(teacherService.get(oldTeacher));
            teacherService.increaseCourses(teacherService.get(newTeacher));
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView getEditForm(@PathVariable long id) {
        ModelAndView modelAndView = new ModelAndView("course/courseForm", "course", service.get(id));
        modelAndView.addObject("teachers", teacherService.getAll());
        modelAndView.addObject("action","Update");
        return modelAndView;
    }

    @RequestMapping(value = "/confirmRemoval{id}", method = RequestMethod.GET)
    public ModelAndView getRemoveConfirmation(@PathVariable long id) {
        Course course = service.get(id);
        if (course.getExams() > 0) {
            ModelAndView modelAndView = new ModelAndView("course/removeCourseError", "course", course);
            String errorMessageOne = "There is 1 exam planned for course " + course.getInfo() + ". This exam needs to be removed before course " + course.getCode() + " can be removed.";
            String errorMessageMany = "There are " + course.getExams() + " exams planned for course " + course.getInfo() + ". They need to be removed before course " + course.getCode() + " can be removed.";
            modelAndView.addObject("examError", course.getExams() <= 1 ? errorMessageOne : errorMessageMany);
            return modelAndView;
        }

        return new ModelAndView("course/removeCourse", "course", course);
    }

    @RequestMapping(value = "/remove{id}", method = RequestMethod.GET)
    public String remove(@PathVariable long id) {
        teacherService.decreaseCourses(teacherService.get(service.get(id).getTeacher()));
        service.delete(id);
        return "redirect:/course.htm";
    }
}
