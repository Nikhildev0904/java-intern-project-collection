package com.cognitree.internship.cms.repository;

import com.cognitree.internship.cms.model.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ContactRepository extends MongoRepository<Contact, String> {

    Optional<Contact> findByPhone(String phone);

    Page<Contact> findByContactNameContainingIgnoreCase(String contactName, Pageable pageable);

    Page<Contact> findByPhoneContaining(String phone, Pageable pageable);

    Page<Contact> findByCategoryIdsIn(String categoryName, Pageable pageable);
}