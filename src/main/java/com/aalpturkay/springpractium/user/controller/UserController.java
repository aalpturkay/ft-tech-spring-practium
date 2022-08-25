package com.aalpturkay.springpractium.user.controller;

import com.aalpturkay.springpractium.productcomment.dto.ProductCommentResponse;
import com.aalpturkay.springpractium.productcomment.dto.ProductCommentSaveRequest;
import com.aalpturkay.springpractium.user.dto.UserSaveRequest;
import com.aalpturkay.springpractium.user.entity.User;
import com.aalpturkay.springpractium.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> findAll(){
        return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping
    public ResponseEntity<User> save(@RequestBody UserSaveRequest userSaveRequest){
        return new ResponseEntity<>(userService.save(userSaveRequest), HttpStatus.CREATED);
    }

    @PostMapping("/comment")
    public ResponseEntity<ProductCommentResponse> addComment(@RequestBody ProductCommentSaveRequest productCommentSaveRequest){
        return new ResponseEntity<>(userService.addComment(productCommentSaveRequest), HttpStatus.CREATED);
    }

    @GetMapping("/{userId}/comments")
    public ResponseEntity<List<ProductCommentResponse>> getComments(@PathVariable Long userId,
                                                            @RequestParam(required = false, defaultValue = "") @DateTimeFormat(pattern = "dd-MM-yyyy")
                                                            String startDate,
                                                            @RequestParam(required = false, defaultValue = "") @DateTimeFormat(pattern = "dd-MM-yyyy")
                                                                String endDate){
        return ResponseEntity.ok(userService.getComments(userId, startDate, endDate));
    }

}
