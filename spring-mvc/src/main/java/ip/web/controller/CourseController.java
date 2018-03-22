package ip.web.controller;

import ip.domain.Course;
import ip.service.CourseService;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/course")
public class CourseController {
    private CourseService service;

    public CourseController(@Autowired CourseService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getCourses() {
        return new ModelAndView("course/overview", "courses", service.getAll());
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public ModelAndView getNewForm() {
        return new ModelAndView("course/courseForm", "course", new Course());
    }

    @RequestMapping(method = RequestMethod.POST)
    public String save(Course course, BindingResult result) {
        if (result.hasErrors()) {
            return "course/courseForm";
        }
        service.add(course);
        return "redirect:/course.htm";
    }

    @RequestMapping(value = "/{code}", method = RequestMethod.GET)
    public ModelAndView getEditForm(@PathVariable String code) {
        return new ModelAndView("course/courseForm", "course", service.get(code));
    }
}
