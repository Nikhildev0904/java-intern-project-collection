package com.cognitree.internship.cms.service;

import com.cognitree.internship.cms.dto.ContactCreateDTO;
import com.cognitree.internship.cms.dto.ContactUpdateDTO;
import com.cognitree.internship.cms.dto.PagedResponse;
import com.cognitree.internship.cms.exception.ResourceAlreadyExistsException;
import com.cognitree.internship.cms.exception.ResourceNotFoundException;
import com.cognitree.internship.cms.model.Contact;
import com.cognitree.internship.cms.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ContactService {

    private final ContactRepository contactRepository;

    @Autowired
    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public PagedResponse<Contact> getAllContacts(
            String contactName, String phone, String categoryName,
            int page, int size, String sortBy, String sortOrder) {
        Sort.Direction direction = sortOrder.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Contact> contacts;
        if (contactName != null && !contactName.isEmpty()) {
            contacts = contactRepository.findByContactNameContainingIgnoreCase(contactName, pageable);
        } else if (phone != null && !phone.isEmpty()) {
            contacts = contactRepository.findByPhoneContaining(phone, pageable);
        } else if (categoryName != null && !categoryName.isEmpty()) {
            contacts = contactRepository.findByCategoryIdsIn(categoryName, pageable);
        } else {
            contacts = contactRepository.findAll(pageable);
        }
        return PagedResponse.fromPage(contacts);
    }

    public Contact getContactById(String id) {
        Contact contact = findContactById(id);
        return contact;
    }


    public Contact createContact(ContactCreateDTO createDTO) {
        Optional<Contact> existingContact = contactRepository.findByPhone(createDTO.getPhone());
        if (existingContact.isPresent()) {
            throw new ResourceAlreadyExistsException("Contact with phone number " + createDTO.getPhone() + " already exists");
        }
        Contact contact = new Contact();
        contact.setContactName(createDTO.getContactName());
        contact.setPhone(createDTO.getPhone());
        contact.setEmail(createDTO.getEmail());
        contact.setCategoryIds(createDTO.getCategoryIds());
        Contact savedContact = contactRepository.save(contact);
        return savedContact;
    }


    public Contact updateContact(String id, ContactUpdateDTO updateDTO) {
        Contact contact = findContactById(id);
        if (updateDTO.getContactName() != null) {
            contact.setContactName(updateDTO.getContactName());
        }
        if (updateDTO.getPhone() != null) {
            // Check if the new phone number already exists for another contact
            Optional<Contact> existingContact = contactRepository.findByPhone(updateDTO.getPhone());
            if (existingContact.isPresent() && !existingContact.get().getId().equals(id)) {
                throw new ResourceAlreadyExistsException("Contact with phone number " + updateDTO.getPhone() + " already exists");
            }
            contact.setPhone(updateDTO.getPhone());
        }
        if (updateDTO.getEmail() != null) {
            contact.setEmail(updateDTO.getEmail());
        }
        if (updateDTO.getCategoryIds() != null) {
            contact.setCategoryIds(updateDTO.getCategoryIds());
        }
        Contact updatedContact = contactRepository.save(contact);
        return updatedContact;
    }


    public void deleteContact(String id) {
        Contact contact = findContactById(id);
        contactRepository.delete(contact);
    }

    private Contact findContactById(String id) {
        return contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact not found with id: " + id));
    }
}