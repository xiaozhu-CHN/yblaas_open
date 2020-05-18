package com.yiban.yblaas.mapper;

import com.yiban.yblaas.domain.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: yblaas
 * @description: 假条的Mapper
 * @author: xiaozhu
 * @create: 2020-03-21 13:27
 **/
@Mapper
@Component
public interface LeaveMapper {
    public List<LeaveStudent> selectLeaveByStudentId(String studentId,Integer number);
    public Integer insertLeave(NewLeave newLeave);
    public LeaveStudentQuery selectLeaveStudentQueryById(Long leaveId);
    public LeaveTeacherQuery selectLeaveTeacherQueryByNumberId(String numberId);
    public LeaveTeacherStep selectLeaveTeacherStepById(Long leaveId);
    public List<LeaveTeacherQuery> selectLeaveTeacherQueryListByDsh(String teacherId, Integer collegeId, String roles);
    public Integer updateLeaveState(Long leaveId, String teacherId, String state, String roles);
    public LeaveTeacherQuery selectLeaveTeacherQueryById(Long leaveId);
    public List<LeaveTeacherQuery> selectLeaveTeacherQueryListByDxj(String teacherId, Integer collegeId, String roles, Integer dayXyld, Integer dayXgc);
    public Integer updateLeaveStateXj(Long leaveId, String teacherId);
    public List<LeaveTeacherQuery> selectLeaveTeacherQueryListAll(Integer column, String dir,String teacherId, Integer collegeId, String roles, Integer type, String seachString, Long seacherLong);
    public Long selectLeaveTeacherQueryListAllNum(String teacherId, Integer collegeId, String roles);
    public Long selectLeaveNum(Integer type);
    public Long selectLeaveNowNum(Integer type);
    public Integer selectLeaveTeacherQueryListByDshNum(String teacherId, Integer collegeId, String roles);
    public Integer selectLeaveByStudentIdNum(Long leaveId, String studentId);
    public String selectLeaveStudentIdById(Long leaveId);
}
