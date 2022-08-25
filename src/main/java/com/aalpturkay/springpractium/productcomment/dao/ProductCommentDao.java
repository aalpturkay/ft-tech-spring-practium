package com.aalpturkay.springpractium.productcomment.dao;

import com.aalpturkay.springpractium.product.entity.Product;
import com.aalpturkay.springpractium.productcomment.entity.ProductComment;
import com.aalpturkay.springpractium.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ProductCommentDao extends JpaRepository<ProductComment, Long> {
    List<ProductComment> findByUser(User user);
    List<ProductComment> findByProduct(Product product);

    List<ProductComment> findAllByProductAndCommentDateBetween(Product product, Date startDate, Date endDate);

    List<ProductComment> findAllByUserAndCommentDateBetween(User user, Date startDate, Date endDate);
}
