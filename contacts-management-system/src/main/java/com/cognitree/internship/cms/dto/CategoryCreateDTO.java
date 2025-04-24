package com.cognitree.internship.cms.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryCreateDTO {

    @NotBlank(message = "Category name is required")
    private String categoryName;

    private String description;

}
