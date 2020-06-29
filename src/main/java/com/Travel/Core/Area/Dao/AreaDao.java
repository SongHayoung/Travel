package com.Travel.Core.Area.Dao;

import com.Travel.Core.Area.VO.AreaVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface AreaDao {
    void addArea(AreaVO area);
    void deleteArea(AreaVO area);

    @Transactional(readOnly = true)
    List<String> getAreas(String id);

    @Transactional(readOnly = true)
    List<String> getAllAreas();
}
