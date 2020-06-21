package com.Travel.biz.User.Dao;

import com.Travel.biz.User.Domain.User;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoJdbc implements UserDao {
    @Autowired
    @Qualifier("sqlSession")
    private SqlSession sqlSession;

    public void setSqlSession(SqlSession sqlSession) { this.sqlSession = sqlSession; }

    public void add(User user) {
        sqlSession.insert("insertUser", user);
    }

    public User get(String id) {
        return (User)sqlSession.selectOne("getUserById", id);
    }

    public void update(User user) {
        sqlSession.update("updateUser", user);
    }
}
