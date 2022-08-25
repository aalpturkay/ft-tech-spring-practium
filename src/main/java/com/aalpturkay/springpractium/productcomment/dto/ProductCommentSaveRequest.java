package com.aalpturkay.springpractium.productcomment.dto;

import lombok.Data;

@Data
public class ProductCommentSaveRequest {
    private String comment;

    private Long productId;

    private Long userId;
}
