package com.example.springcrud.service;

import com.example.springcrud.dto.ProductDto;
import com.example.springcrud.entity.Product;
import com.example.springcrud.exception.ResourceNotFoundException;
import com.example.springcrud.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    // -------------------- GET ALL --------------------
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // -------------------- GET BY ID --------------------
    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        return toDto(product);
    }

    // -------------------- CREATE --------------------
    public ProductDto createProduct(ProductDto dto) {
        Product product = toEntity(dto);
        Product saved = productRepository.save(product);
        return toDto(saved);
    }

    // -------------------- UPDATE (PUT - full replace) --------------------
    public ProductDto updateProduct(Long id, ProductDto dto) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        existing.setName(dto.getName());
        existing.setDescription(dto.getDescription());
        existing.setPrice(dto.getPrice());
        existing.setQuantity(dto.getQuantity());
        existing.setCategory(dto.getCategory());

        Product updated = productRepository.save(existing);
        return toDto(updated);
    }

    // -------------------- PATCH (partial update) --------------------
    public ProductDto patchProduct(Long id, ProductDto dto) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        if (dto.getName() != null) existing.setName(dto.getName());
        if (dto.getDescription() != null) existing.setDescription(dto.getDescription());
        if (dto.getPrice() != null) existing.setPrice(dto.getPrice());
        if (dto.getQuantity() != null) existing.setQuantity(dto.getQuantity());
        if (dto.getCategory() != null) existing.setCategory(dto.getCategory());

        Product updated = productRepository.save(existing);
        return toDto(updated);
    }

    // -------------------- DELETE --------------------
    public void deleteProduct(Long id) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        productRepository.delete(existing);
    }

    // -------------------- MAPPING HELPERS --------------------
    private ProductDto toDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .category(product.getCategory())
                .build();
    }

    private Product toEntity(ProductDto dto) {
        return Product.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .quantity(dto.getQuantity())
                .category(dto.getCategory())
                .build();
    }
}
