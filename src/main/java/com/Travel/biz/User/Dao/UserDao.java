package com.Travel.biz.User.Dao;

import com.Travel.biz.User.Domain.User;

public interface UserDao {
    void add(User user);
    User get(String id);
    void update(User user);
}
