package com.cognitree.internship.cms.services;

import com.cognitree.internship.cms.dto.ContactCreateDTO;
import com.cognitree.internship.cms.dto.ContactUpdateDTO;
import com.cognitree.internship.cms.models.PagedResponse;
import com.cognitree.internship.cms.exceptions.ResourceAlreadyExistsException;
import com.cognitree.internship.cms.exceptions.ResourceNotFoundException;
import com.cognitree.internship.cms.models.Category;
import com.cognitree.internship.cms.models.Contact;
import com.cognitree.internship.cms.repositories.CategoryRepository;
import com.cognitree.internship.cms.repositories.ContactRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ContactService {

    private final ContactRepository contactRepository;
    private final CategoryRepository categoryRepository;
    private static final Logger logger = LoggerFactory.getLogger(ContactService.class);

    @Autowired
    public ContactService(ContactRepository contactRepository, CategoryRepository categoryRepository) {
        this.contactRepository = contactRepository;
        this.categoryRepository = categoryRepository;
    }

    public PagedResponse<Contact> getAllContacts(
            String contactName, String phone, String categoryName,
            int page, int size, String sortBy, String sortOrder) {
        logger.debug("Fetching contacts with filters - name: {}, phone: {}, categoryName: {}, page: {}", 
                    contactName, phone, categoryName, page);
        Sort.Direction direction = sortOrder.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Contact> contacts;

        if (contactName != null && !contactName.isEmpty()) {
            logger.debug("Searching contacts by name: {}", contactName);
            contacts = contactRepository.findByContactNameContainingIgnoreCase(contactName, pageable);
        } else if (phone != null && !phone.isEmpty()) {
            logger.debug("Searching contacts by phone: {}", phone);
            contacts = contactRepository.findByPhoneContaining(phone, pageable);
        } else if (categoryName != null && !categoryName.isEmpty()) {
            logger.debug("Searching contacts by category name: {}", categoryName);
            List<Category> categories = categoryRepository.findByCategoryNameContainingIgnoreCase(categoryName, pageable).getContent();
            List<String> categoryIds = categories.stream().map(Category::getId).toList();
            List<Contact> contactList = new ArrayList<>();
            for (String categoryId : categoryIds) {
                contactList.addAll(contactRepository.findByCategoryIdsIn(categoryId, pageable).getContent());
            }
            contacts = contactList.isEmpty() ? Page.empty() : new PageImpl<>(contactList, PageRequest.of(page, size, sort), contactList.size());
        } else {
            logger.debug("Fetching all contacts");
            contacts = contactRepository.findAll(pageable);
        }
        logger.debug("Found {} contacts", contacts.getTotalElements());
        return PagedResponse.fromPage(contacts);
    }

    public Contact createContact(ContactCreateDTO contactCreateDTO) {
        logger.debug("Creating new contact with phone: {}", contactCreateDTO.getPhone());
        Optional<Contact> existingContact = contactRepository.findByPhone(contactCreateDTO.getPhone());
        if (existingContact.isPresent()) {
            logger.error("Contact already exists with phone: {}", contactCreateDTO.getPhone());
            throw new ResourceAlreadyExistsException("Contact with phone number " + contactCreateDTO.getPhone() + " already exists");
        }
        Contact contact = new Contact();
        contact.setContactName(contactCreateDTO.getContactName());
        contact.setPhone(contactCreateDTO.getPhone());
        contact.setEmail(contactCreateDTO.getEmail());
        if (contactCreateDTO.getCategoryIds() != null && !contactCreateDTO.getCategoryIds().isEmpty()) {
            List<String> categoryIds = contactCreateDTO.getCategoryIds();
            for (String categoryId : categoryIds) {
                if (!categoryRepository.existsById(categoryId)) {
                    logger.error("Category not found with ID: {}", categoryId);
                    throw new ResourceNotFoundException("Category not found with id: " + categoryId);
                }
            }
            contact.setCategoryIds(categoryIds);
            logger.debug("Assigning {} categories to new contact", categoryIds.size());
        }
        Contact savedContact = contactRepository.save(contact);
        logger.info("Created new contact with ID: {}", savedContact.getId());
        return savedContact;
    }

    public Contact getContactById(String id) {
        logger.debug("Fetching contact by ID: {}", id);
        Contact contact = findContactById(id);
        return contact;
    }

    public Contact updateContact(String id, ContactUpdateDTO contactUpdateDTO) {
        logger.debug("Updating contact with ID: {}", id);
        Contact contact = findContactById(id);
        if (contactUpdateDTO.getContactName() != null) {
            contact.setContactName(contactUpdateDTO.getContactName());
        }
        if (contactUpdateDTO.getPhone() != null) {
            logger.debug("Updating phone number for contact: {}", id);
            Optional<Contact> existingContact = contactRepository.findByPhone(contactUpdateDTO.getPhone());
            if (existingContact.isPresent() && !existingContact.get().getId().equals(id)) {
                logger.error("Phone number already exists: {}", contactUpdateDTO.getPhone());
                throw new ResourceAlreadyExistsException("Contact with phone number " + contactUpdateDTO.getPhone() + " already exists");
            }
            contact.setPhone(contactUpdateDTO.getPhone());
        }
        if (contactUpdateDTO.getEmail() != null) {
            contact.setEmail(contactUpdateDTO.getEmail());
        }
        if (contactUpdateDTO.getCategoryIds() != null) {
            logger.debug("Updating categories for contact: {}", id);
            contact.setCategoryIds(contactUpdateDTO.getCategoryIds());
        }
        Contact updatedContact = contactRepository.save(contact);
        logger.info("Updated contact with ID: {}", id);
        return updatedContact;
    }

    public void deleteContact(String contactId) {
        logger.debug("Attempting to delete contact with ID: {}", contactId);
        if (!contactRepository.existsById(contactId)) {
            logger.error("Contact not found with ID: {}", contactId);
            throw new ResourceNotFoundException("Contact not found with contactId: " + contactId);
        }
        Contact contact = findContactById(contactId);
        contactRepository.delete(contact);
        logger.info("Deleted contact with ID: {}", contactId);
    }

    public PagedResponse<Category> getContactCategories(
            String contactId,
            String categoryName,
            int page, int pageSize,
            String sortBy, String sortOrder
    ) {
        logger.debug("Fetching categories for contact: {}, name filter: {}, page: {}", 
                    contactId, categoryName, page);
        Contact contact = getContactById(contactId);
        List<String> categoryIds = contact.getCategoryIds();
        if (categoryIds.isEmpty()) {
            logger.debug("No categories found for contact: {}", contactId);
            return PagedResponse.fromPage(Page.empty());
        }
        Sort.Direction dir = sortOrder.equalsIgnoreCase("desc")
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(dir, sortBy));
        Page<Category> categoriesPage;
        if (StringUtils.hasText(categoryName)) {
            logger.debug("Searching categories by name: {} for contact: {}", categoryName, contactId);
            categoriesPage = categoryRepository
                    .findByIdInAndCategoryNameContainingIgnoreCase(
                            categoryIds, categoryName, pageable);
        } else {
            logger.debug("Fetching all categories for contact: {}", contactId);
            categoriesPage = categoryRepository
                    .findByIdIn(categoryIds, pageable);
        }
        logger.debug("Found {} categories for contact {}", categoriesPage.getTotalElements(), contactId);
        return PagedResponse.fromPage(categoriesPage);
    }

    public Contact addCategoryToContact(String contactId, String categoryId) {
        logger.debug("Adding category {} to contact {}", categoryId, contactId);
        Contact contact = getContactById(contactId);
        categoryRepository.findById(categoryId)
                .orElseThrow(() -> {
                    logger.error("Category not found with ID: {}", categoryId);
                    return new ResourceNotFoundException("Category not found");
                });
        boolean categoryExists = contact.getCategoryIds().contains(categoryId);
        if (!categoryExists) {
            contact.getCategoryIds().add(categoryId);
            contactRepository.save(contact);
            logger.info("Added category {} to contact {}", categoryId, contactId);
        } else {
            logger.debug("Category {} already assigned to contact {}", categoryId, contactId);
        }
        return contact;
    }

    public void removeCategoryFromContact(String contactId, String categoryId) {
        logger.debug("Removing category {} from contact {}", categoryId, contactId);
        Contact contact = getContactById(contactId);
        boolean removed = contact.getCategoryIds().remove(categoryId);
        if (!removed) {
            logger.error("Category {} not associated with contact {}", categoryId, contactId);
            throw new ResourceNotFoundException("Category not associated with this contact");
        }
        contactRepository.save(contact);
        logger.info("Removed category {} from contact {}", categoryId, contactId);
    }

    private Contact findContactById(String id) {
        return contactRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Contact not found with ID: {}", id);
                    return new ResourceNotFoundException("Contact not found with id: " + id);
                });
    }
}