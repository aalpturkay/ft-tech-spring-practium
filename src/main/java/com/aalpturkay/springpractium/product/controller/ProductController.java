package com.aalpturkay.springpractium.product.controller;

import com.aalpturkay.springpractium.product.dto.ProductResponse;
import com.aalpturkay.springpractium.product.dto.ProductSaveRequest;
import com.aalpturkay.springpractium.product.entity.Product;
import com.aalpturkay.springpractium.product.service.ProductService;
import com.aalpturkay.springpractium.productcomment.dto.ProductCommentResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> save(@RequestBody ProductSaveRequest productSaveRequest){
        ProductResponse productResponse = productService.save(productSaveRequest);
        return new ResponseEntity<>(productResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{productId}/comments")
    public ResponseEntity<List<ProductCommentResponse>> getComments(@PathVariable Long productId,
                                                                    @RequestParam(required = false, defaultValue = "") @DateTimeFormat(pattern = "dd-MM-yyyy")
                                                                    String startDate,
                                                                    @RequestParam(required = false, defaultValue = "") @DateTimeFormat(pattern = "dd-MM-yyyy")
                                                                        String endDate){

        List<ProductCommentResponse> commentsByProductId = productService.getCommentsByProductId(productId, startDate, endDate);
        return ResponseEntity.ok(commentsByProductId);
    }

    @GetMapping("/expired")
    private ResponseEntity<List<Product>> getExpiredProducts(){
        return ResponseEntity.ok(productService.getExpiredProducts());
    }

    @GetMapping("/notExpired")
    private ResponseEntity<List<Product>> getNotExpiredProducts(){
        return ResponseEntity.ok(productService.getNotExpiredProducts());
    }
}
