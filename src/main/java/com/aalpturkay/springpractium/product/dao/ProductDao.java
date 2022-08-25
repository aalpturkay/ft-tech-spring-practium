package com.aalpturkay.springpractium.product.dao;

import com.aalpturkay.springpractium.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface ProductDao extends JpaRepository<Product, Long> {
    @Query("select a from Product a where a.expireDate < :today")
    List<Product> findAllExpired(@Param("today") Date today);

    @Query("select a from Product a where (a.expireDate >= :today or a.expireDate is null)")
    List<Product> findAllNotExpired(@Param("today") Date today);
}
