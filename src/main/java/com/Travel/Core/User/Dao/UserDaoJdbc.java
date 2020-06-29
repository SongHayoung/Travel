package com.Travel.Core.User.Dao;

import com.Travel.Core.User.VO.UserVO;
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

    private static final int DUPLICATE_USER_ID = 1;
    private static final int DUPLICATE_USER_EMAIL = 1;

    public void addUser(UserVO user) {
        sqlSession.insert("insertUser", user);
    }

    public UserVO getUser(String id) throws NoSuchUserException {
        UserVO targetUser = (UserVO)sqlSession.selectOne("getUserById", id);
        if(targetUser == null)
            throw new NoSuchUserException();

        return targetUser;
    }

    public void updateUser(UserVO user) {
        sqlSession.update("updateUser", user);
    }

    public void deleteUser(UserVO user) { sqlSession.delete("deleteUser", user); }

    public List<UserVO> getUserFriend(String id) { return sqlSession.selectList("getUserFriends", id); }

    public int getUserCount() { return sqlSession.selectOne("getUserCount"); }

    public boolean isIdExists(String id) {
        int exists = sqlSession.selectOne("getIdExists", id);
        if(exists == DUPLICATE_USER_ID)
            return false;
        else
            return true;
    }

    public boolean isEmailExists(String email) {
        int exists = sqlSession.selectOne("getEmailExists", email);
        if(exists == DUPLICATE_USER_EMAIL)
            return false;
        else
            return true;
    }
}
