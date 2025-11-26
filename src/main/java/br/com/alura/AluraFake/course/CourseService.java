package br.com.alura.AluraFake.course;

import br.com.alura.AluraFake.task.Task;
import br.com.alura.AluraFake.task.TaskRepository;
import br.com.alura.AluraFake.task.Type;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final TaskRepository taskRepository;

    public CourseService(CourseRepository courseRepository, TaskRepository taskRepository) {
        this.courseRepository = courseRepository;
        this.taskRepository = taskRepository;
    }

    @Transactional
    public void publishCourse(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        if (course.getStatus() != Status.BUILDING) {
            throw new IllegalArgumentException("Course must be in BUILDING status to be published");
        }

        List<Task> tasks = taskRepository.findByCourseOrderByOrderNumberAsc(course);

        if (tasks.isEmpty()) {
            throw new IllegalArgumentException("Course must have tasks to be published");
        }

        boolean hasOpenText = false;
        boolean hasSingleChoice = false;
        boolean hasMultipleChoice = false;

        int expectedOrder = 1;

        for (Task task : tasks) {
            if (task.getOrderNumber() != expectedOrder) {
                throw new IllegalArgumentException("Tasks must have continuous order starting from 1");
            }
            expectedOrder++;

            if (task.getType() == Type.OPEN_TEXT) {
                hasOpenText = true;
            } else if (task.getType() == Type.SINGLE_CHOICE) {
                hasSingleChoice = true;
            } else if (task.getType() == Type.MULTIPLE_CHOICE) {
                hasMultipleChoice = true;
            }
        }

        if (!hasOpenText || !hasSingleChoice || !hasMultipleChoice) {
            throw new IllegalArgumentException("Course must have at least one task of each type to be published");
        }

        course.setStatus(Status.PUBLISHED);
        course.setPublishedAt(LocalDateTime.now());

        courseRepository.save(course);
    }
}