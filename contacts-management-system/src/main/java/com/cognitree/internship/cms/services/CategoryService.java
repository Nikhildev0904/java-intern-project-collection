package com.cognitree.internship.cms.services;

import com.cognitree.internship.cms.dto.CategoryCreateDTO;
import com.cognitree.internship.cms.dto.CategoryUpdateDTO;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ContactRepository contactRepository;
    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, ContactRepository contactRepository) {
        this.categoryRepository = categoryRepository;
        this.contactRepository = contactRepository;
    }

    public PagedResponse<Category> getAllCategories(String categoryName, int page, int size,
                                                    String sortBy, String sortOrder) {
        logger.debug("Fetching categories with name: {}, page: {}, size: {}, sortBy: {}, sortOrder: {}", 
                    categoryName, page, size, sortBy, sortOrder);
        Sort.Direction direction = sortOrder.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        Page<Category> categoriesPage;
        if (categoryName != null && !categoryName.isEmpty()) {
            logger.debug("Searching categories by name: {}", categoryName);
            categoriesPage = categoryRepository.findByCategoryNameContainingIgnoreCase(categoryName, pageable);
        } else {
            logger.debug("Fetching all categories");
            categoriesPage = categoryRepository.findAll(pageable);
        }
        logger.debug("Found {} categories", categoriesPage.getTotalElements());
        return PagedResponse.fromPage(categoriesPage);
    }

    public Category getCategoryById(String categoryId) {
        logger.debug("Fetching category by ID: {}", categoryId);
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> {
                    logger.error("Category not found with ID: {}", categoryId);
                    return new ResourceNotFoundException("Category not found with id: " + categoryId);
                });
        logger.debug("Found category: {}", category.getCategoryName());
        return category;
    }

    public Category createCategory(CategoryCreateDTO categoryCreateDTO) {
        logger.debug("Creating new category with name: {}", categoryCreateDTO.getCategoryName());
        if (categoryRepository.existsByCategoryNameIgnoreCase(categoryCreateDTO.getCategoryName())) {
            logger.error("Category already exists with name: {}", categoryCreateDTO.getCategoryName());
            throw new ResourceAlreadyExistsException("Category with name: " + categoryCreateDTO.getCategoryName() + " already exists");
        }
        Category category = new Category();
        category.setCategoryName(categoryCreateDTO.getCategoryName());
        category.setDescription(categoryCreateDTO.getDescription());
        Category savedCategory = categoryRepository.save(category);
        logger.info("Created new category with ID: {}", savedCategory.getId());
        return savedCategory;
    }

    public Category updateCategory(String categoryId, CategoryUpdateDTO categoryUpdateDTO) {
        logger.debug("Updating category with ID: {}", categoryId);
        Category existingCategory = getCategoryById(categoryId);
        if (categoryUpdateDTO.getCategoryName() != null) {
            if (!existingCategory.getCategoryName().equalsIgnoreCase(categoryUpdateDTO.getCategoryName()) &&
                    categoryRepository.existsByCategoryNameIgnoreCase(categoryUpdateDTO.getCategoryName())) {
                logger.error("Cannot update category. Name already exists: {}", categoryUpdateDTO.getCategoryName());
                throw new ResourceAlreadyExistsException("Category with name: " + categoryUpdateDTO.getCategoryName() + " already exists");
            }
            existingCategory.setCategoryName(categoryUpdateDTO.getCategoryName());
        }
        if (categoryUpdateDTO.getDescription() != null) {
            existingCategory.setDescription(categoryUpdateDTO.getDescription());
        }
        Category updatedCategory = categoryRepository.save(existingCategory);
        logger.info("Updated category with ID: {}", categoryId);
        return updatedCategory;
    }

    public void deleteCategory(String categoryId) {
        logger.debug("Attempting to delete category with ID: {}", categoryId);
        if (!categoryRepository.existsById(categoryId)) {
            logger.error("Cannot delete category. Not found with ID: {}", categoryId);
            throw new ResourceNotFoundException("Category not found with id: " + categoryId);
        }
        categoryRepository.deleteById(categoryId);
        logger.info("Deleted category with ID: {}", categoryId);
    }

    public PagedResponse<Contact> getCategoryContacts(String categoryId, String contactName, String phone,
                                                      int page, int size, String sortBy, String sortOrder) {
        logger.debug("Fetching contacts for category: {}, name: {}, phone: {}, page: {}", 
                    categoryId, contactName, phone, page);
        getCategoryById(categoryId);
        Sort.Direction direction = sortOrder.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        Page<Contact> contactsPage;
        if (contactName != null && !contactName.isEmpty()) {
            logger.debug("Searching contacts by name: {} in category: {}", contactName, categoryId);
            contactsPage = contactRepository.findByCategoryIdsInAndContactNameContainingIgnoreCase(categoryId, contactName, pageable);
        } else if (phone != null && !phone.isEmpty()) {
            logger.debug("Searching contacts by phone: {} in category: {}", phone, categoryId);
            contactsPage = contactRepository.findByCategoryIdsInAndPhoneContaining(categoryId, phone, pageable);
        } else {
            logger.debug("Fetching all contacts in category: {}", categoryId);
            contactsPage = contactRepository.findByCategoryIdsIn(categoryId, pageable);
        }
        logger.debug("Found {} contacts in category {}", contactsPage.getTotalElements(), categoryId);
        return PagedResponse.fromPage(contactsPage);
    }
}
