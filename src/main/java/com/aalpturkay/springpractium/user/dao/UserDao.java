package com.aalpturkay.springpractium.user.dao;

import com.aalpturkay.springpractium.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Long> {
}
