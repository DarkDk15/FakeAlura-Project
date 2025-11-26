package br.com.alura.AluraFake.task;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/task/new/opentext")
    public ResponseEntity<?> newOpenTextExercise(@RequestBody @Valid NewOpenTextTaskDTO dto) {
        taskService.createOpenTextTask(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/task/new/singlechoice")
    public ResponseEntity<?> newSingleChoice(@RequestBody @Valid NewSingleChoiceTaskDTO dto) {
        taskService.createSingleChoiceTask(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/task/new/multiplechoice")
    public ResponseEntity<?> newMultipleChoice(@RequestBody @Valid NewMultipleChoiceTaskDTO dto) {
        taskService.createMultipleChoiceTask(dto);
        return ResponseEntity.status(201).build();
    }

}