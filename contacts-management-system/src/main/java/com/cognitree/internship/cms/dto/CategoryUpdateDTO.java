package com.cognitree.internship.cms.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryUpdateDTO {

    private String categoryName;

    private String description;
}
