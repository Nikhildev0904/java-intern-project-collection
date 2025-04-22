package com.cognitree.internship.cms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactUpdateDTO {

    private String contactName;

    private String phone;

    private String email;

    private List<String> categoryIds = new ArrayList<>();
}