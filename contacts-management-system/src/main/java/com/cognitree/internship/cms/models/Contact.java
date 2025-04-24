package com.cognitree.internship.cms.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "contacts")
public class Contact {
    @Id
    private String id;

    private String contactName;

    @Indexed(unique = true)
    private String phone;

    private String email;

    private List<String> categoryIds = new ArrayList<>();
}