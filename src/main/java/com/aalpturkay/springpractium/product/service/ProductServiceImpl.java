package com.aalpturkay.springpractium.product.service;

import com.aalpturkay.springpractium.product.dao.ProductDao;
import com.aalpturkay.springpractium.product.dto.ProductResponse;
import com.aalpturkay.springpractium.product.dto.ProductSaveRequest;
import com.aalpturkay.springpractium.product.entity.Product;
import com.aalpturkay.springpractium.productcomment.dao.ProductCommentDao;
import com.aalpturkay.springpractium.productcomment.dto.ProductCommentResponse;
import com.aalpturkay.springpractium.productcomment.entity.ProductComment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductDao productDao;
    private final ProductCommentDao productCommentDao;

    @Override
    public ProductResponse save(ProductSaveRequest saveRequest) {
        Product product = new Product();

        product.setName(saveRequest.getName());
        product.setPrice(saveRequest.getPrice());
        product.setExpireDate(saveRequest.getExpireDate());
        product = productDao.save(product);

        return ProductResponse.builder()
                .id(product.getId()).name(product.getName()).price(product.getPrice())
                .expireDate(product.getExpireDate()).build();
    }

    @Override
    public List<ProductCommentResponse> getCommentsByProductId(Long productId, String startDate, String endDate) {
        Product product = productDao.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

        List<ProductComment> productComments = (startDate.isEmpty() || endDate.isEmpty()) ? productCommentDao.findByProduct(product):
                getProductCommentsBetweenDates(product, startDate, endDate);

        List<ProductCommentResponse> productCommentResponseList = new ArrayList<>();

        if (!productComments.isEmpty()){
            for (ProductComment productComment : productComments) {
                if(productComment == null) continue;
                ProductCommentResponse productCommentResponse = ProductCommentResponse.builder()
                        .id(productComment.getId()).comment(productComment.getComment())
                        .commentDate(productComment.getCommentDate()).build();

                productCommentResponseList.add(productCommentResponse);
            }
        }

        return productCommentResponseList;
    }

    @Override
    public List<Product> getExpiredProducts() {
        Calendar calendar = Calendar.getInstance();

        return productDao.findAllExpired(calendar.getTime());
    }

    @Override
    public List<Product> getNotExpiredProducts() {
        Calendar calendar = Calendar.getInstance();

        return productDao.findAllNotExpired(calendar.getTime());
    }

    private List<ProductComment> getProductCommentsBetweenDates(Product product, String startDate, String endDate){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date formattedStartDate, formattedEndDate;

        try {
            formattedStartDate = simpleDateFormat.parse(startDate);
            formattedEndDate = simpleDateFormat.parse(endDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return productCommentDao.findAllByProductAndCommentDateBetween(product,
                formattedStartDate, formattedEndDate);
    }
}
