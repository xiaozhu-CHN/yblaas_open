package com.yiban.yblaas.service;

import com.yiban.yblaas.domain.*;

import java.util.Date;

/**
 * @program: yblaas
 * @description: 学生Service接口
 * @author: xiaozhu
 * @create: 2020-03-21 17:30
 **/
public interface StudentService {
    DataTables<LeaveStudent> getStudentLeave(Integer start, Integer length, Integer number);

    Boolean newLeaveView();

    String getstudentName();

    String addNewLeave(NewLeave newLeave);

    LeaveStudentQuery getLeaveById(Long leaveId);

    String getStudentExamine();

    Student getStudent();

    Eclass getEclassByEclassId(Integer eclassId);

    String getCollegeName(Integer collegeId);

    String changeStudentData(Student student, String verification);

    String getStudentQq();

    String getStudentEmail();

    String changeStudentQq(String studentQq, String verification);

    String changeStudentEmail(String studentEmail, String verification);

    DataTables<AttendanceStudent> getStudentAttendance(Integer start, Integer length, Integer number);

    Attendance getAttendance(Long attendanceId);

    Date getAttendanceInfoTime(Long attendanceId);

    String getDbConfigAttendanceAccuracy();

    String addAttendanceInfo(AttendanceInfo attendanceInfo);

    String changeCancelLeave(Long leaveId);

    String cancelStudentEmail();
  }
