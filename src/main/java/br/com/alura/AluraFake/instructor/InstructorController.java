package br.com.alura.AluraFake.instructor;

import br.com.alura.AluraFake.course.Course;
import br.com.alura.AluraFake.course.CourseRepository;
import br.com.alura.AluraFake.course.Status;
import br.com.alura.AluraFake.task.TaskRepository;
import br.com.alura.AluraFake.user.User;
import br.com.alura.AluraFake.user.UserRepository;
import br.com.alura.AluraFake.util.ErrorItemDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class InstructorController {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final TaskRepository taskRepository;

    public InstructorController(UserRepository userRepository,
                                CourseRepository courseRepository,
                                TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.taskRepository = taskRepository;
    }

    @GetMapping("/instructor/{id}/courses")
    public ResponseEntity<?> getInstructorCourses(@PathVariable("id") Long id) {

        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorItemDTO("id", "User not found"));
        }

        if (!user.isInstructor()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorItemDTO("id", "User is not an instructor"));
        }

        List<Course> courses = courseRepository.findByInstructor(user);

        List<InstructorCourseItemDTO> courseItems = courses.stream()
                .map(course -> {
                    long taskCount = taskRepository.countByCourse(course);
                    return new InstructorCourseItemDTO(course, taskCount);
                })
                .toList();

        long totalPublished = courses.stream()
                .filter(c -> c.getStatus() == Status.PUBLISHED)
                .count();

        InstructorCoursesReportDTO report = new InstructorCoursesReportDTO(courseItems, totalPublished);

        return ResponseEntity.ok(report);
    }
}
