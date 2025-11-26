package br.com.alura.AluraFake.instructor;

import java.util.List;

public class InstructorCoursesReportDTO {

    private List<InstructorCourseItemDTO> courses;
    private long totalPublished;

    public InstructorCoursesReportDTO(List<InstructorCourseItemDTO> courses, long totalPublished) {
        this.courses = courses;
        this.totalPublished = totalPublished;
    }

    public List<InstructorCourseItemDTO> getCourses() {
        return courses;
    }

    public long getTotalPublished() {
        return totalPublished;
    }
}