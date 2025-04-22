package com.cognitree.internship.cms.controller;

import com.cognitree.internship.cms.dto.ContactCreateDTO;
import com.cognitree.internship.cms.dto.ContactUpdateDTO;
import com.cognitree.internship.cms.dto.PagedResponse;
import com.cognitree.internship.cms.model.Contact;
import com.cognitree.internship.cms.service.ContactService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    private final ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping
    public ResponseEntity<PagedResponse<Contact>> getAllContacts(
            @RequestParam(required = false) String contactName,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String categoryName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int pageSize,
            @RequestParam(defaultValue = "contactName") String sortBy,
            @RequestParam(defaultValue = "asc") String sortOrder) {

        PagedResponse<Contact> response = contactService.getAllContacts(
                contactName, phone, categoryName, page, pageSize, sortBy, sortOrder);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{contactId}")
    public ResponseEntity<Contact> getContactById(@PathVariable String contactId) {
        Contact contact = contactService.getContactById(contactId);
        return ResponseEntity.ok(contact);
    }

    @PostMapping
    public ResponseEntity<Contact> createContact(@Valid @RequestBody ContactCreateDTO createDTO) {
        Contact createdContact = contactService.createContact(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdContact);
    }

    @PutMapping("/{contactId}")
    public ResponseEntity<Contact> updateContact(
            @PathVariable String contactId,
            @Valid @RequestBody ContactUpdateDTO updateDTO) {

        Contact updatedContact = contactService.updateContact(contactId, updateDTO);
        return ResponseEntity.ok(updatedContact);
    }

    @DeleteMapping("/{contactId}")
    public ResponseEntity<Void> deleteContact(@PathVariable String contactId) {
        contactService.deleteContact(contactId);
        return ResponseEntity.noContent().build();
    }
}