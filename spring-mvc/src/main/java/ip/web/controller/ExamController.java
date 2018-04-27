package ip.web.controller;

import ip.domain.Exam;
import ip.service.ClassroomService;
import ip.service.CourseService;
import ip.service.ExamService;
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
        modelAndView.addObject("action", "Add");
        return modelAndView;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView save(@Valid Exam exam, BindingResult result) {
        String isAvailable = (exam.getClassroom() > 0 ? service.classroomIsStillAvailable(exam.getId(), exam.getClassroom(), exam.getDate(), exam.getBeginTime(), exam.getEndTime()) : null);
        ModelAndView modelAndView = new ModelAndView("exam/examForm", "exam", exam);
        modelAndView.addObject("courses", courseService.getAll());
        modelAndView.addObject("classrooms", classroomService.getAll());
        modelAndView.addObject("action", exam.getId() == 0 ? "Add" : "Update");
        if (result.hasErrors() || !(isAvailable == null)) {
            if (!(isAvailable == null)) {
                result.rejectValue("endTime", "error.endTime", isAvailable);
            }
            return modelAndView;
        }
        if (exam.getId() == 0) {
            if (service.alreadyExists(exam)) {
                result.rejectValue("id", "error.id", "This exam already exists.");
                return modelAndView;
            }
            service.add(exam);
            classroomService.increaseExams(classroomService.get(exam.getClassroom()));
            courseService.increaseExams(courseService.get(exam.getCourse()));

        } else {
            updateClassroomAndCourse(exam);
            service.update(exam);
        }

        return new ModelAndView("redirect:/exam.htm");
    }

    private void updateClassroomAndCourse(Exam exam) {
        long oldClassroom = service.get(exam.getId()).getClassroom();
        long newClassroom = exam.getClassroom();
        long oldCourse = service.get(exam.getId()).getCourse();
        long newCourse = exam.getCourse();

        if (oldClassroom != newClassroom) {
            classroomService.decreaseExams(classroomService.get(oldClassroom));
            classroomService.increaseExams(classroomService.get(newClassroom));
        }

        if (oldCourse != newCourse) {
            courseService.decreaseExams(courseService.get(oldCourse));
            courseService.increaseExams(courseService.get(newCourse));
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView getEditForm(@PathVariable long id) {
        ModelAndView modelAndView = new ModelAndView("exam/examForm", "exam", service.get(id));
        modelAndView.addObject("courses", courseService.getAll());
        modelAndView.addObject("classrooms", classroomService.getAll());
        modelAndView.addObject("action", "Update");
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
        classroomService.decreaseExams(classroomService.get(service.get(id).getClassroom()));
        courseService.decreaseExams(courseService.get(service.get(id).getCourse()));
        service.delete(id);
        return "redirect:/exam.htm";
    }
}
