package com.aalpturkay.springpractium.user.service;

import com.aalpturkay.springpractium.product.entity.Product;
import com.aalpturkay.springpractium.productcomment.dto.ProductCommentSaveRequest;
import com.aalpturkay.springpractium.productcomment.dto.ProductCommentResponse;
import com.aalpturkay.springpractium.productcomment.entity.ProductComment;
import com.aalpturkay.springpractium.user.dto.UserSaveRequest;
import com.aalpturkay.springpractium.user.entity.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User save(UserSaveRequest userSaveRequest);
    ProductCommentResponse addComment(ProductCommentSaveRequest productCommentSaveRequest);
    List<ProductCommentResponse> getComments(Long userId, String startDate, String endDate);
}
