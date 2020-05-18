package com.yiban.yblaas.mapper;

import com.yiban.yblaas.domain.Eclass;
import com.yiban.yblaas.domain.EclassTeacher;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: yblaas
 * @description: 班级表Mapper
 * @author: xiaozhu
 * @create: 2020-03-18 15:42
 **/
@Mapper
@Component
public interface EclassMapper {
    public Integer selectEclassId(String name);
    public List<Eclass> selectEclassByCollegeId(Integer collegeId);
    public Eclass selectEclass(Integer eclassId);
    public List<EclassTeacher> selectEclassTeacherByTeacherId(String teacherId);
    public List<EclassTeacher> selectEclassTeacherByCollegeId(Integer collegeId);
    public Integer updateEclassTeacherId(String teacherId, Integer eclassId);
    public Integer selectEclassByTeacherIdNum(String teacherId);
    public Integer insertEclass(Integer collegeId, String eclassName);
    public Integer updateEclassName(String eclassName,Integer eclassId);
    public Integer deleteEclass(Integer eclassId);
}
