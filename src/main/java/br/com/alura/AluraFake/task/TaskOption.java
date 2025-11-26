package br.com.alura.AluraFake.task;

import jakarta.persistence.*;

@Entity
public class TaskOption {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 80)
	private String optionText;
	
	@Column(nullable = false)
	private boolean isCorrect;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "task_id")
	private Task task;
	
	@Deprecated
	public TaskOption() {
	}
	
	public TaskOption(String optionText, boolean isCorrect, Task task) {
        this.optionText = optionText;
        this.isCorrect = isCorrect;
        this.task = task;
    }

    public Long getId() {
        return id;
    }

    public String getOptionText() {
        return optionText;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public Task getTask() {
        return task;
    }
}
