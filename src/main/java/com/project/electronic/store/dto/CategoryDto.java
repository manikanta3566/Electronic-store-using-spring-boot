package com.project.electronic.store.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class CategoryDto {

    private String id;

    @NotEmpty(message = "title should not be empty")
    private String title;

    @NotEmpty(message = "description should not be empty")
    private String description;

    private LocalDateTime createdDate;

    private String coverImagePath;
}
