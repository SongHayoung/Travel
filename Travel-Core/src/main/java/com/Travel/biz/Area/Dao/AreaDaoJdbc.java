package com.Travel.biz.Area.Dao;

import com.Travel.biz.Area.VO.AreaVO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AreaDaoJdbc implements AreaDao{
    @Autowired
    @Qualifier("sqlSession")
    private SqlSession sqlSession;

    public void addArea(AreaVO area) {
        sqlSession.insert("insertArea", area);
    }
    public void deleteArea(AreaVO area) {
        sqlSession.delete("deleteArea", area);
    }
    public List<String> getAreas(String id) {
        return sqlSession.selectList("getAreas", id);
    }

    public List<String> getAllAreas() {
        return sqlSession.selectList("getAllAreas");
    }
}
