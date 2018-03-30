package ip.web.controller;

import ip.domain.Exam;
import ip.service.ClassroomService;
import ip.service.CourseService;
import ip.service.ExamService;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import javax.validation.Valid;
import java.util.ArrayList;

@Controller
@RequestMapping(value = "/exam")
public class ExamController {
    private ExamService service;
    private CourseService courseService;
    private ClassroomService classroomService;

    public ExamController(@Autowired ExamService service, @Autowired CourseService courseService, @Autowired ClassroomService classroomService) {
        this.service = service;
        this.courseService = courseService;
        this.classroomService = classroomService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getExams() {
        ArrayList<Exam> exams = new ArrayList<>();
        for (Exam exam: service.getAll()) {
            exam.setAttribute("course", courseService.get(exam.getCourse()));
            exam.setAttribute("classroom", classroomService.get(exam.getClassroom()));
            exams.add(exam);
        }
        return new ModelAndView("exam/overview", "exams", exams);
    }


    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public ModelAndView getNewForm() {
        ModelAndView modelAndView = new ModelAndView("exam/examForm", "exam", new Exam());
        modelAndView.addObject("courses",courseService.getAll());
        modelAndView.addObject("classrooms",classroomService.getAll());
        return modelAndView;
        }

    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public String save(@Valid Exam exam, BindingResult result) {
        if (result.hasErrors()) {
            return "exam/examForm";
        }
        if (exam.getId() == 0) {
            service.add(exam);
        } else {
            service.update(exam);
        }
        return "redirect:/exam.htm";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView getEditForm(@PathVariable long id) {
        return new ModelAndView("exam/examForm", "exam", service.get(id));
    }

    @RequestMapping(value = "/confirmRemoval{id}", method = RequestMethod.GET)
    public ModelAndView getRemoveConfirmation(@PathVariable long id) {
        ModelAndView modelAndView = new ModelAndView("exam/removeExam", "exam", service.get(id));
        modelAndView.addObject("course", courseService.get(service.get(id).getCourse()).getName());
        return modelAndView;
    }

    @RequestMapping(value = "/remove{id}", method = RequestMethod.GET)
    public String remove(@PathVariable long id) {
        service.delete(id);
        return "redirect:/exam.htm";
    }
}
