package com.yiban.yblaas.mapper;

import com.yiban.yblaas.domain.LeaveTeacherStudent;
import com.yiban.yblaas.domain.Student;
import com.yiban.yblaas.domain.TeacherStudent;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: yblaas
 * @description: 学生表Mapper
 * @author: xiaozhu
 * @create: 2020-03-18 14:35
 **/
@Mapper
@Component
public interface StudentMapper {
    public Integer selectByStudentId(String studentId);
    public String selectStudentName(String studentId);
    public Integer insertStudent(Student student);
    public String selectStudentExamine(String studentId);
    public Student selectStudent(String studentId);
    public Integer updateStudent(Student student);
    public String selectStudentQq(String studentId);
    public String selectStudentEmail(String studentId);
    public Integer updateStudentQq(String studentId, String studentQq);
    public Integer updateStudentEmail(String studentId, String studentEmail);
    public LeaveTeacherStudent selectLeaveTeacherStudentByLeaveId(Long LeaveId);
    public Integer selectStudentNumByEclassId(Integer eclassId);
    public List<Student> selectStudentListDsh(String teacherId);
    public Integer updateStudentExamine(String studentId, String examine);
    public String selectStudentTeacherId(String studentId);
    public List<TeacherStudent> selectTeacherStudentList(Integer eclassId);
    public Integer updateStudentEclassNull(Integer eclassId);
    public Integer selectStudentListDshNum(String teacherId);
    public List<Student> selectStudentByEclassId(Integer eclassId);
}
