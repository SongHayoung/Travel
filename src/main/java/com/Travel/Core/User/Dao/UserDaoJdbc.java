package com.Travel.Core.User.Dao;

import com.Travel.Core.User.VO.UserVO;
import com.Travel.biz.MyPage.Controller.DuplicateUserIDException;
import io.swagger.models.auth.In;
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

    private static final int DUPLICATE_KEY = 1;

    public void addUser(UserVO user) {
        isEmailExists(user.getEmail());
        isIdExists(user.getId());
        sqlSession.insert("insertUser", user);
        sqlSession.insert("insertUserAuth", user);
    }

    public UserVO getUser(String id) throws NoValueException {
        UserVO targetUser = (UserVO) sqlSession.selectOne("getUserById", id);
        if (targetUser == null)
            throw new NoValueException(id);
        targetUser.setRoles(getUserAuth(id));


        return targetUser;
    }

    public UserVO getUser(int userSid) throws NoValueException {
        UserVO targetUser = (UserVO) sqlSession.selectOne("getUserBySid", userSid);
        if (targetUser == null)
            throw new NoValueException("USERSID : " + Integer.toString(userSid));
        targetUser.setRoles(getUserAuth(targetUser.getId()));


        return targetUser;
    }

    private List<String> getUserAuth(String id) {
        return sqlSession.selectList("getUserAuth", id);
    }

    public void updateUser(UserVO user) {
        sqlSession.update("updateUser", user);
    }

    public void deleteUser(UserVO user) {
        sqlSession.delete("deleteUser", user);
    }

    public List<UserVO> getUserFollowings(List<Integer> follows) {
        return sqlSession.selectList("getUserFollowings", follows);
    }

    public int getUserCount() {
        return sqlSession.selectOne("getUserCount");
    }

    public void isIdExists(String id) throws DuplicateKeyException {
        int exists = sqlSession.selectOne("getIdExists", id);
        if (exists == DUPLICATE_KEY)
            throw new DuplicateKeyException(id);
    }

    public void isEmailExists(String email) throws DuplicateKeyException {
        int exists = sqlSession.selectOne("getEmailExists", email);
        if (exists == DUPLICATE_KEY)
            throw new DuplicateUserIDException(email);
    }

    public void addFollowing(Integer userSid) {
        sqlSession.update("addFollowing", userSid);
    }

    public void deleteFollowing(Integer userSid) {
        sqlSession.update("deleteFollowing", userSid);
    }

    public void addFollower(Integer userSid) {
        sqlSession.update("addFollower", userSid);
    }

    public void deleteFollower(Integer userSid) {
        sqlSession.update("deleteFollower", userSid);
    }
}
