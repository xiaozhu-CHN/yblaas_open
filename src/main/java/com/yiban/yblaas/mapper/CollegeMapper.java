package com.yiban.yblaas.mapper;

import com.yiban.yblaas.domain.College;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: yblaas
 * @description: 学院表Mapper
 * @author: xiaozhu
 * @create: 2020-03-18 15:39
 **/
@Mapper
@Component
public interface CollegeMapper {
    public Integer selectCollegeId(String name);
    public List<College> selectCollegeAll();
    public String selectCollegeName(Integer collegeId);
    public Integer insertCollege(String collegeName);
    public Integer updateCollege(String collegeName, Integer collegeId);
    public Integer deleteCollege(Integer collegeId);
}
