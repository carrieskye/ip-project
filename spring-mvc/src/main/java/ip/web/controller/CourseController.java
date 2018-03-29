package ip.web.controller;

import ip.domain.Course;
import ip.domain.Teacher;
import ip.service.CourseService;
import ip.service.TeacherService;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;

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
        for (Course course: service.getAll()) {
            course.setAttribute("teacher", teacherService.get(course.getTeacher()));
            courses.add(course);
        }

        return new ModelAndView("course/overview", "courses", courses);
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public ModelAndView getNewForm() {
        ModelAndView modelAndView = new ModelAndView("course/courseForm", "course", new Course());
        modelAndView.addObject("teachers",teacherService.getAll());
        return modelAndView;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@Valid Course course, BindingResult result) {
        if (result.hasErrors()) {
            return "course/courseForm";
        }
        if (course.getId() == 0) {
            service.add(course);
        } else {
            service.update(course);
        }
        return "redirect:/course.htm";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView getEditForm(@PathVariable long id) {
        return new ModelAndView("course/courseForm", "course", service.get(id));
    }
}
