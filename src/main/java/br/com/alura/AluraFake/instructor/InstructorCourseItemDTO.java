package br.com.alura.AluraFake.instructor;

import java.time.LocalDateTime;

import br.com.alura.AluraFake.course.Course;
import br.com.alura.AluraFake.course.Status;

public class InstructorCourseItemDTO {

    private Long id;
    private String title;
    private Status status;
    private LocalDateTime publishedAt;
    private long taskCount;

    public InstructorCourseItemDTO(Course course, long taskCount) {
        this.id = course.getId();
        this.title = course.getTitle();
        this.status = course.getStatus();
        this.publishedAt = course.getPublishedAt();
        this.taskCount = taskCount;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Status getStatus() {
        return status;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    public long getTaskCount() {
        return taskCount;
    }
}