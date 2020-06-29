package com.Travel.Core.Area.Dao;

import com.Travel.Core.Area.VO.AreaVO;

import java.util.List;

public interface AreaDao {
    void addArea(AreaVO area);
    void deleteArea(AreaVO area);
    List<String> getAreas(String id);
    List<String> getAllAreas();
}
