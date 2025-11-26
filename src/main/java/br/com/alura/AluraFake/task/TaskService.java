package br.com.alura.AluraFake.task;

import br.com.alura.AluraFake.course.Course;
import br.com.alura.AluraFake.course.CourseRepository;
import br.com.alura.AluraFake.course.Status;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final CourseRepository courseRepository;

    public TaskService(TaskRepository taskRepository, CourseRepository courseRepository) {
        this.taskRepository = taskRepository;
        this.courseRepository = courseRepository;
    }

    @Transactional
    public void createOpenTextTask(NewOpenTextTaskDTO dto) {
    	
        Course course = courseRepository.findById(dto.courseId())
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        if (course.getStatus() != Status.BUILDING) {
            throw new IllegalArgumentException("Course must be in BUILDING status to receive tasks");
        }

        if (taskRepository.existsByCourseAndStatement(course, dto.statement())) {
            throw new IllegalArgumentException("There is already a task with this statement for this course");
        }

        List<Task> tasks = taskRepository.findByCourseOrderByOrderNumberAsc(course);
        int currentSize = tasks.size();
        int desiredOrder = dto.order();

        if (desiredOrder > currentSize + 1) {
            throw new IllegalArgumentException("Invalid order sequence for tasks");
        }

        for (Task t : tasks) {
            if (t.getOrderNumber() >= desiredOrder) {
                t.setOrderNumber(t.getOrderNumber() + 1);
            }
        }

        Task task = new Task(dto.statement(), desiredOrder, Type.OPEN_TEXT, course);

        taskRepository.save(task);
    }
    
    @Transactional
    public void createSingleChoiceTask(NewSingleChoiceTaskDTO dto) {

        Course course = courseRepository.findById(dto.courseId())
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        if (course.getStatus() != Status.BUILDING) {
            throw new IllegalArgumentException("Course must be in BUILDING status to receive tasks");
        }

        if (taskRepository.existsByCourseAndStatement(course, dto.statement())) {
            throw new IllegalArgumentException("There is already a task with this statement for this course");
        }

        var options = dto.options();

        long correctCount = options.stream()
                .filter(NewChoiceOptionDTO::isCorrect)
                .count();

        if (correctCount != 1) {
            throw new IllegalArgumentException("Single choice task must have exactly one correct option");
        }

        var seen = new java.util.HashSet<String>();
        for (NewChoiceOptionDTO opt : options) {
            String text = opt.option();

            if (text.equals(dto.statement())) {
                throw new IllegalArgumentException("Option text cannot be equal to the task statement");
            }

            if (!seen.add(text)) {
                throw new IllegalArgumentException("Options cannot be duplicated");
            }
        }

        List<Task> tasks = taskRepository.findByCourseOrderByOrderNumberAsc(course);
        int currentSize = tasks.size();
        int desiredOrder = dto.order();

        if (desiredOrder > currentSize + 1) {
            throw new IllegalArgumentException("Invalid order sequence for tasks");
        }

        for (Task t : tasks) {
            if (t.getOrderNumber() >= desiredOrder) {
                t.setOrderNumber(t.getOrderNumber() + 1);
            }
        }

        Task task = new Task(dto.statement(), desiredOrder, Type.SINGLE_CHOICE, course);

        for (NewChoiceOptionDTO opt : options) {
            TaskOption option = new TaskOption(opt.option(), opt.isCorrect(), task);
            task.getOptions().add(option);
        }

        taskRepository.save(task);
    }
    
    @Transactional
    public void createMultipleChoiceTask(NewMultipleChoiceTaskDTO dto) {
        Course course = courseRepository.findById(dto.courseId())
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        if (course.getStatus() != Status.BUILDING) {
            throw new IllegalArgumentException("Course must be in BUILDING status to receive tasks");
        }

        if (taskRepository.existsByCourseAndStatement(course, dto.statement())) {
            throw new IllegalArgumentException("There is already a task with this statement for this course");
        }

        var options = dto.options();

        int correctCount = 0;
        int incorrectCount = 0;

        java.util.Set<String> texts = new java.util.HashSet<>();

        for (NewChoiceOptionDTO opt : options) {
            String text = opt.option();

            if (text == null || text.isBlank() || text.length() < 4 || text.length() > 80) {
                throw new IllegalArgumentException("Each option must have between 4 and 80 characters");
            }

            if (text.equalsIgnoreCase(dto.statement())) {
                throw new IllegalArgumentException("Options cannot be equal to the statement");
            }

            if (!texts.add(text)) {
                throw new IllegalArgumentException("Options must be unique within the task");
            }

            if (opt.isCorrect()) {
                correctCount++;
            } else {
                incorrectCount++;
            }
        }

        if (correctCount < 2) {
            throw new IllegalArgumentException("Multiple choice task must have at least 2 correct options");
        }

        if (incorrectCount < 1) {
            throw new IllegalArgumentException("Multiple choice task must have at least 1 incorrect option");
        }

        List<Task> tasks = taskRepository.findByCourseOrderByOrderNumberAsc(course);
        int currentSize = tasks.size();
        int desiredOrder = dto.order();

        if (desiredOrder > currentSize + 1) {
            throw new IllegalArgumentException("Invalid order sequence for tasks");
        }

        for (Task t : tasks) {
            if (t.getOrderNumber() >= desiredOrder) {
                t.setOrderNumber(t.getOrderNumber() + 1);
            }
        }

        Task task = new Task(dto.statement(), desiredOrder, Type.MULTIPLE_CHOICE, course);

        for (NewChoiceOptionDTO opt : options) {
            task.getOptions().add(new TaskOption(opt.option(), opt.isCorrect(), task));
        }

        taskRepository.save(task);
    }
    
}
