package br.com.alura.AluraFake.course;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alura.AluraFake.user.User;

public interface CourseRepository extends JpaRepository<Course, Long>{

	List<Course> findByInstructor(User instructor);
	
}
