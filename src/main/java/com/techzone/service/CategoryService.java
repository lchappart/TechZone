package com.techzone.service;

import com.techzone.dto.CategoryCreateDTO;
import com.techzone.dto.CategoryDTO;
import com.techzone.entity.Category;
import com.techzone.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(CategoryDTO::fromEntity)
                .collect(Collectors.toList());
    }
    
    public CategoryDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Catégorie non trouvée"));
        return CategoryDTO.fromEntity(category);
    }
    
    @Transactional
    public CategoryDTO createCategory(CategoryCreateDTO dto) {
        if (categoryRepository.findByNom(dto.getNom()).isPresent()) {
            throw new RuntimeException("Une catégorie avec ce nom existe déjà");
        }
        
        Category category = new Category();
        category.setNom(dto.getNom());
        category.setDescription(dto.getDescription());
        category = categoryRepository.save(category);
        return CategoryDTO.fromEntity(category);
    }
    
    @Transactional
    public CategoryDTO updateCategory(Long id, CategoryCreateDTO dto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Catégorie non trouvée"));
        
        category.setNom(dto.getNom());
        category.setDescription(dto.getDescription());
        category = categoryRepository.save(category);
        return CategoryDTO.fromEntity(category);
    }
    
    @Transactional
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Catégorie non trouvée"));
        
        if (!category.getProducts().isEmpty()) {
            throw new RuntimeException("Impossible de supprimer une catégorie contenant des produits");
        }
        
        categoryRepository.delete(category);
    }
}
