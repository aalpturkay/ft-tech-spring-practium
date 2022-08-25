package com.aalpturkay.springpractium.product.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class ProductSaveRequest {
    private String name;
    private BigDecimal price;
    private Date expireDate;
}
