package com.Travel.biz.Area.Dao;

import com.Travel.biz.Area.VO.AreaVO;

import java.util.List;

public interface AreaDao {
    void addArea(AreaVO area);
    void deleteArea(AreaVO area);
    List<String> getAreas(String id);
    List<String> getAllAreas();
}
