package com.Travel.biz.User.Dao;

import com.Travel.biz.User.VO.UserVO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoJdbc implements UserDao {
    @Autowired
    @Qualifier("sqlSession")
    private SqlSession sqlSession;

    public void setSqlSession(SqlSession sqlSession) { this.sqlSession = sqlSession; }

    public void addUser(UserVO user) {
        sqlSession.insert("insertUser", user);
    }

    public UserVO getUser(String id) {
        return (UserVO)sqlSession.selectOne("getUserById", id);
    }

    public void updateUser(UserVO user) {
        sqlSession.update("updateUser", user);
    }

    public void deleteUser(UserVO user) { sqlSession.delete("deleteUser", user); }

    public List<UserVO> getUserFriend(String id) { return sqlSession.selectList("getUserFriends", id); }

    public int getUserCount() { return sqlSession.selectOne("getUserCount"); }

    public int getIdExists(String id) { return sqlSession.selectOne("getIdExists", id); }

    public int getEmailExists(String email) { return sqlSession.selectOne("getEmailExists", email); }
}
