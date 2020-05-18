package com.yiban.yblaas.mapper;

import com.yiban.yblaas.domain.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: yblaas
 * @description: 老师表的接口
 * @author: xiaozhu
 * @create: 2020-03-13 20:23
 **/
@Mapper
@Component
public interface TeacherMapper {
    public Integer selectByTeacherId(String teacherId);
    public String selectTeacherName(String teacherId);
    public Integer insertTeacher(Teacher teacher);
    public Teacher selectTeacher(String teacherId);
    public Integer updateTeacherQq(String teacherId, String teacherQq);
    public Integer updateTeacherEmail(String teacherId, String teacherEmail);
    public Integer updateTeacher(Teacher teacher);
    public List<Teacher> selectTeacherByCollegeIdFdy(Integer collegeId);
    public List<Teacher> selectTeacherByCollegeIdXyld(Integer collegeId);
    public List<Teacher> selectTeacherByXgc();
    public List<Teacher> selectTeacherByCollegeIdRoleXyld(Integer collegeId);
    public List<Teacher> selectTeacherByRoleXgc();
}
