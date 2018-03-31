package ip.web.controller;

import ip.domain.Course;
import ip.domain.Exam;
import ip.service.ClassroomService;
import ip.service.CourseService;
import ip.service.ExamService;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
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
        for (Exam exam : service.getAll()) {
            exam.setAttribute("course", courseService.get(exam.getCourse()));
            exam.setAttribute("classroom", classroomService.get(exam.getClassroom()));
            exams.add(exam);
        }
        return new ModelAndView("exam/overview", "exams", exams);
    }


    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public ModelAndView getNewForm() {
        ModelAndView modelAndView = new ModelAndView("exam/examForm", "exam", new Exam());
        modelAndView.addObject("courses", courseService.getAll());
        modelAndView.addObject("classrooms", classroomService.getAll());
        return modelAndView;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView save(@Valid Exam exam, BindingResult result) {
        String isAvailable = classroomService.get(exam.getClassroom()).isAvailable(exam.getDate(), exam.getBegin(), exam.getEnd());
        if (result.hasErrors() || !(isAvailable == null) || service.alreadyExists(exam)) {
            if (service.alreadyExists(exam)) {
                result.rejectValue("id", "error.id", "This exam already exists.");
            } else if (!(isAvailable == null)) {
                result.rejectValue("end", "error.end", isAvailable);
            }
            ModelAndView modelAndView = new ModelAndView("exam/examForm", "exam", exam);
            modelAndView.addObject("courses", courseService.getAll());
            modelAndView.addObject("classrooms", classroomService.getAll());
            return modelAndView;
        }

        if (exam.getId() == 0) {
            service.add(exam);
            classroomService.get(exam.getClassroom()).increaseExams();

        } else {
            updateClassroomAndCourse(exam);
            service.update(exam);
        }
        classroomService.get(exam.getClassroom()).occupation(exam.getId(), exam.getDate(), exam.getBegin(), exam.getEnd());

        return getExams();
    }

    private void updateClassroomAndCourse(Exam exam) {
        long oldClassroom = service.get(exam.getId()).getClassroom();
        long newClassroom = exam.getClassroom();
        long oldCourse = service.get(exam.getId()).getCourse();
        long newCourse = exam.getCourse();

        if (oldClassroom != newClassroom) {
            classroomService.get(oldClassroom).decreaseExams();
            classroomService.get(newClassroom).increaseExams();
        }

        if (oldCourse != newCourse) {
            courseService.get(oldCourse).decreaseExams();
            courseService.get(newCourse).increaseExams();
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView getEditForm(@PathVariable long id) {
        ModelAndView modelAndView = new ModelAndView("exam/examForm", "exam", service.get(id));
        modelAndView.addObject("courses", courseService.getAll());
        modelAndView.addObject("classrooms", classroomService.getAll());
        return modelAndView;
    }

    @RequestMapping(value = "/confirmRemoval{id}", method = RequestMethod.GET)
    public ModelAndView getRemoveConfirmation(@PathVariable long id) {
        ModelAndView modelAndView = new ModelAndView("exam/removeExam", "exam", service.get(id));
        modelAndView.addObject("course", courseService.get(service.get(id).getCourse()).getName());
        return modelAndView;
    }

    @RequestMapping(value = "/remove{id}", method = RequestMethod.GET)
    public String remove(@PathVariable long id) {
        classroomService.get(service.get(id).getClassroom()).decreaseExams();
        courseService.get(service.get(id).getCourse()).decreaseExams();
        service.delete(id);
        return "redirect:/exam.htm";
    }
}
