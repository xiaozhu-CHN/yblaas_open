package com.yiban.yblaas.mapper;

import com.yiban.yblaas.domain.AttendanceInfo;
import com.yiban.yblaas.domain.TeacherAttendance;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @program: yblaas
 * @description: 考勤信息表Mapper
 * @author: xiaozhu
 * @create: 2020-04-05 23:26
 **/
@Mapper
@Component
public interface AttendanceInfoMapper {
    public List<TeacherAttendance> selectTeacherAttendanceById(Long attendanceId);
    public Integer selectAttendanceInfoNumByAttendanceId(Long attendanceId);
    public Integer insertAttendanceInfoTeacher(Long attendanceId, String studentId);
    public Integer deleteAttendanceInfoTeacher(Long attendanceId, String studentId);
    public Date selectAttendanceInfoTime(Long attendanceId, String studentId);
    public Integer selectAttendanceInfoMac(Long attendanceId, String mac);
    public Integer insertAttendanceInfoStudent(AttendanceInfo attendanceInfo);
    public Integer selectAttendanceInfoNum(Integer type);
}
