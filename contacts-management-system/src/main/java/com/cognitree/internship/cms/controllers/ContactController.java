package com.cognitree.internship.cms.controllers;

import com.cognitree.internship.cms.dto.ContactCreateDTO;
import com.cognitree.internship.cms.dto.ContactUpdateDTO;
import com.cognitree.internship.cms.models.PagedResponse;
import com.cognitree.internship.cms.models.Category;
import com.cognitree.internship.cms.models.Contact;
import com.cognitree.internship.cms.services.ContactService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    private final ContactService contactService;
    private static final Logger logger = LoggerFactory.getLogger(ContactController.class);

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
            @RequestParam(defaultValue = "asc") String sortOrder
    ) {
        logger.info("Fetching contacts with filters: contactName={}, phone={}, categoryName={}, page={}", 
                    contactName, phone, categoryName, page);
        PagedResponse<Contact> response = contactService.getAllContacts(
                contactName, phone, categoryName, page, pageSize, sortBy, sortOrder);
        logger.debug("Fetched {} contacts", response.getTotalElements());
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Contact> createContact(@Valid @RequestBody ContactCreateDTO createDTO) {
        logger.info("Creating new contact with name: {}", createDTO.getContactName());
        Contact createdContact = contactService.createContact(createDTO);
        logger.info("Created contact with ID: {}", createdContact.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdContact);
    }

    @GetMapping("/{contactId}")
    public ResponseEntity<Contact> getContactById(@PathVariable String contactId) {
        logger.info("Fetching contact with ID: {}", contactId);
        Contact contact = contactService.getContactById(contactId);
        return ResponseEntity.ok(contact);
    }

    @PutMapping("/{contactId}")
    public ResponseEntity<Contact> updateContact(
            @PathVariable String contactId,
            @Valid @RequestBody ContactUpdateDTO updateDTO) {
        logger.info("Updating contact with ID: {}", contactId);
        Contact updatedContact = contactService.updateContact(contactId, updateDTO);
        logger.info("Successfully updated contact: {}", contactId);
        return ResponseEntity.ok(updatedContact);
    }

    @DeleteMapping("/{contactId}")
    public ResponseEntity<Void> deleteContact(@PathVariable String contactId) {
        logger.info("Deleting contact with ID: {}", contactId);
        contactService.deleteContact(contactId);
        logger.info("Successfully deleted contact: {}", contactId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{contactId}/categories")
    public ResponseEntity<PagedResponse<Category>> getContactCategories(
            @PathVariable String contactId,
            @RequestParam(required = false) String categoryName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int pageSize,
            @RequestParam(defaultValue = "categoryName") String sortBy,
            @RequestParam(defaultValue = "asc") String sortOrder) {
        logger.info("Fetching categories for contact ID: {} with name filter: {}", contactId, categoryName);
        PagedResponse<Category> categories = contactService.getContactCategories(
                contactId, categoryName, page, pageSize, sortBy, sortOrder);
        logger.debug("Found {} categories for contact {}", categories.getTotalElements(), contactId);
        return ResponseEntity.ok(categories);
    }

    @PutMapping("/{contactId}/categories/{categoryId}")
    public ResponseEntity<Contact> assignCategoryToContact(
            @PathVariable String contactId,
            @PathVariable String categoryId) {
        logger.info("Assigning category {} to contact {}", categoryId, contactId);
        Contact updatedContact = contactService.addCategoryToContact(contactId, categoryId);
        logger.info("Successfully assigned category {} to contact {}", categoryId, contactId);
        return ResponseEntity.ok().body(updatedContact);
    }

    @DeleteMapping("/{contactId}/categories/{categoryId}")
    public ResponseEntity<Void> removeCategoryFromContact(
            @PathVariable String contactId,
            @PathVariable String categoryId) {
        logger.info("Removing category {} from contact {}", categoryId, contactId);
        contactService.removeCategoryFromContact(contactId, categoryId);
        logger.info("Successfully removed category {} from contact {}", categoryId, contactId);
        return ResponseEntity.noContent().build();
    }
}