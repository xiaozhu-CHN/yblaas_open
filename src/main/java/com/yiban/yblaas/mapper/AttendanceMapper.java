package com.yiban.yblaas.mapper;

import com.yiban.yblaas.domain.Attendance;
import com.yiban.yblaas.domain.AttendanceStudent;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: yblaas
 * @description: 考勤表Mapper接口
 * @author: xiaozhu
 * @create: 2020-04-05 13:36
 **/
@Mapper
@Component
public interface AttendanceMapper {
    public Integer insertAttendance(Attendance attendance);
    public List<Attendance> selectAttendanceListByTeacher(String teacherId, Integer column, String dir,Integer type, String seachString, Long seacherLong);
    public Long selectAttendanceListByTeacherNum(String teacherId);
    public Attendance selectAttendanceByAttendanceId(Long attendanceId);
    public List<Attendance> selectAttendanceListDkqByTeacher(String teacherId);
    public Integer updateAttendanceStop(Long attendanceId, String state);
    public List<AttendanceStudent> selectAttendanceStudentDkq(Integer eclassId, String studentId);
    public List<AttendanceStudent> selectAttendanceStudentAll(Integer eclassId);
    public Long selectAttendanceNum(Integer type);
}
