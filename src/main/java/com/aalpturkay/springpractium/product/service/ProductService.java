package com.aalpturkay.springpractium.product.service;

import com.aalpturkay.springpractium.product.dto.ProductResponse;
import com.aalpturkay.springpractium.product.dto.ProductSaveRequest;
import com.aalpturkay.springpractium.product.entity.Product;
import com.aalpturkay.springpractium.productcomment.dto.ProductCommentResponse;

import java.util.List;

public interface ProductService {
    ProductResponse save(ProductSaveRequest saveRequest);
    List<ProductCommentResponse> getCommentsByProductId(final Long productId, final String startDate, final String endDate);
    List<Product> getExpiredProducts();
    List<Product> getNotExpiredProducts();
}
