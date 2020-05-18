package com.yiban.yblaas.service;

import com.yiban.yblaas.domain.*;

import java.util.List;

/**
 * @program: yblaas
 * @description: 老师接口类
 * @author: xiaozhu
 * @create: 2020-03-30 23:14
 **/
public interface TeacherService {
    String getTeacherName();

    String getTeacherRoles();

    Teacher getTeacherData();

    String changeTeacherQq(String teacherQq, String verification);

    String changeTeacherEmail(String teacherEmail, String verification);

    String changeTeacher(Teacher teacher, String verification);

    LeaveTeacherQuery getLeaveByNumberId(String numberId);

    LeaveTeacherStep getLeaveStepById(Long leaveId);

    List<LeaveTeacherQuery> getLeaveByDsh();

    String changeLeaveStateDsh(Long leaveId, Boolean result);

    List<LeaveTeacherQuery> getLeaveByDxj();

    String changeLeaveStateDxj(Long leaveId);

    LeaveTeacherStudent getStudentData(Long leaveId);

    DataTables<LeaveTeacherQuery> getLeaveAll(Integer draw, Integer length, Integer start, Integer column, String dir, Integer type, String seachString);

    String getDbConfigAttendanceAccuracy();

    String newAttendance(Attendance attendance);

    DataTables<Attendance> getAttendanceAll(Integer draw, Integer length, Integer start, Integer column, String dir, Integer type, String seachString);

    List<TeacherAttendance> getAttendanceInfoById(Long attendanceId);

    AttendanceInfoData getAttendanceInfoData(Long attendanceId);

    String addAttendanceInfo(Long attendanceId, String studentId);

    String delAttendanceInfo(Long attendanceId, String studentId);

    List<Attendance> getAttendanceDkq();

    String changeAttendanceStop(Long attendanceId);

    String changeAttendanceEnable(Long attendanceId);

    String changeAttendanceDel(Long attendanceId);

    List<Student> getStudentListDsh();

    String changeStudentExamine(String studentId, String examine);

    String changeStudentExamines(String[] studentIds);

    List<EclassTeacher> getEclassTeacherByTeacherId();

    List<TeacherStudent> getTeacherStudentList(Integer eclassId);

    List<EclassTeacher> getEclassTeacherByCollegeId();

    String changeEclassTeacherId(String teacherId, Integer eclassId);

    List<Teacher> getTeacherByCollegeId();

    String delEclassTeacherId(Integer eclassId);

    String addEclass(String eclassName);

    String changeEclassName(String eclassName, Integer eclassId);

    String delEclass(Integer eclassId);

    List<Teacher> getTeacherXyld(Integer collegeId);

    String changeTeacherXyldRole(String teacherId, String role);

    String addCollege(String collegeName);

    String changeCollege(String collegeName, Integer collegeId);

    String delCollege(Integer collegeId);

    Integer getLeaveByDxjNum();

    Integer getStudentDshNum();

    String cancelTeacherEmail();
}
