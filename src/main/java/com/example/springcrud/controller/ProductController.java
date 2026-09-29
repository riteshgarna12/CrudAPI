package com.example.springcrud.controller;

import com.example.springcrud.dto.ProductDto;
import com.example.springcrud.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // GET /api/products — get all products
    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    // GET /api/products/{id} — get product by id
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    // POST /api/products — create new product
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto) {
        ProductDto created = productService.createProduct(productDto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // PUT /api/products/{id} — full update
    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id,
                                                     @Valid @RequestBody ProductDto productDto) {
        return ResponseEntity.ok(productService.updateProduct(id, productDto));
    }

    // PATCH /api/products/{id} — partial update
    @PatchMapping("/{id}")
    public ResponseEntity<ProductDto> patchProduct(@PathVariable Long id,
                                                    @RequestBody ProductDto productDto) {
        return ResponseEntity.ok(productService.patchProduct(id, productDto));
    }

    // DELETE /api/products/{id} — delete product
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully with id: " + id);
    }
}
