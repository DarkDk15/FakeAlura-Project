package br.com.alura.AluraFake.task;

import br.com.alura.AluraFake.course.Course;
import jakarta.persistence.*;

import java.util.List;
import java.time.LocalDateTime;
import java.util.ArrayList;


@Entity
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private LocalDateTime createdAt = LocalDateTime.now();
	
	@Column(nullable = false, length = 255)
	private String statement;
	
	@Column(nullable = false)
	private Integer orderNumber;
	
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Type type;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "course_id")
	private Course course;
	
	@OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<TaskOption> options = new ArrayList<>();
	
	@Deprecated
	public Task() {
	}
	
	public Task(String statement, Integer orderNumber, Type type, Course course) {
		this.statement = statement;
		this.orderNumber = orderNumber;
		this.type = type;
		this.course = course;
	}
	
	public Long getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getStatement() {
        return statement;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Type getType() {
        return type;
    }

    public Course getCourse() {
        return course;
    }

    public List<TaskOption> getOptions() {
        return options;
    }
	
}
