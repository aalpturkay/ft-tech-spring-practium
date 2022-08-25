package com.aalpturkay.springpractium.user.service;

import com.aalpturkay.springpractium.product.dao.ProductDao;
import com.aalpturkay.springpractium.product.entity.Product;
import com.aalpturkay.springpractium.productcomment.dao.ProductCommentDao;
import com.aalpturkay.springpractium.productcomment.dto.ProductCommentResponse;
import com.aalpturkay.springpractium.productcomment.dto.ProductCommentSaveRequest;
import com.aalpturkay.springpractium.productcomment.entity.ProductComment;
import com.aalpturkay.springpractium.user.dao.UserDao;
import com.aalpturkay.springpractium.user.dto.UserSaveRequest;
import com.aalpturkay.springpractium.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final ProductCommentDao productCommentDao;
    private final ProductDao productDao;

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public User save(UserSaveRequest userSaveRequest) {
        User user = new User();

        user.setEmail(userSaveRequest.getEmail());
        user.setName(userSaveRequest.getName());
        user.setSurname(userSaveRequest.getSurname());
        user.setPhoneNumber(userSaveRequest.getPhoneNumber());

        user = userDao.save(user);

        return user;
    }

    @Override
    public ProductCommentResponse addComment(ProductCommentSaveRequest productCommentSaveRequest) {
        ProductComment productComment = new ProductComment();

        User user = userDao.findById(productCommentSaveRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Product product = productDao.findById(productCommentSaveRequest.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        productComment.setUser(user);
        productComment.setProduct(product);
        productComment.setComment(productCommentSaveRequest.getComment());
        productComment.setCommentDate(new Date());
        productComment = productCommentDao.save(productComment);

        return ProductCommentResponse.builder().id(productComment.getId())
                .comment(productComment.getComment()).commentDate(productComment.getCommentDate()).build();
    }

    @Override
    public List<ProductCommentResponse> getComments(Long userId, String startDate, String endDate) {
        User user = userDao.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        List<ProductComment> productComments = (startDate.isEmpty() || endDate.isEmpty()) ? productCommentDao.findByUser(user) :
                getProductCommentsBetweenDates(user, startDate, endDate);

        return convertToProductCommentResponseList(productComments);
    }

    private List<ProductComment> getProductCommentsBetweenDates(User user, String startDate, String endDate){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date formattedStartDate, formattedEndDate;

        try {
            formattedStartDate = simpleDateFormat.parse(startDate);
            formattedEndDate = simpleDateFormat.parse(endDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return productCommentDao.findAllByUserAndCommentDateBetween(user, formattedStartDate, formattedEndDate);
    }

    private List<ProductCommentResponse> convertToProductCommentResponseList(List<ProductComment> productComments){
        List<ProductCommentResponse> productCommentResponseList = new ArrayList<>();

        for (ProductComment productComment : productComments) {
            productCommentResponseList.add(ProductCommentResponse.builder().id(productComment.getId())
                    .comment(productComment.getComment()).commentDate(productComment.getCommentDate()).build());
        }

        return productCommentResponseList;
    }
}
