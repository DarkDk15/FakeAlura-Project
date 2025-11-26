package br.com.alura.AluraFake.task;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;

public record NewSingleChoiceTaskDTO(

        @NotNull
        Long courseId,

        @NotBlank
        @Size(min = 4, max = 255)
        String statement,

        @NotNull
        @Min(1)
        Integer order,

        @NotNull
        @Size(min = 2, max = 5, message = "Single choice task must have between 2 and 5 options")
        List<@Valid NewChoiceOptionDTO> options

) {}