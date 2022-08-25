package com.aalpturkay.springpractium.productcomment.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ProductCommentResponse {
    private Long id;
    private String comment;
    private Date commentDate;
}
