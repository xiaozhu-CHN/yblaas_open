package com.yiban.yblaas.controller;

import com.yiban.yblaas.domain.Attendance;
import com.yiban.yblaas.domain.LeaveTeacherQuery;
import com.yiban.yblaas.domain.Teacher;
import com.yiban.yblaas.service.impl.PublicServiceImpl;
import com.yiban.yblaas.service.impl.TeacherServiceImpl;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: yblaas
 * @description: 老师的Controller
 * @author: xiaozhu
 * @create: 2020-03-30 23:06
 **/
@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherServiceImpl teacherService;
    @Autowired
    private PublicServiceImpl publicService;

    /**
     * 功能描述:
     * (欢迎页)
     *
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/3/14 16:18
     */
    @RequestMapping("welcome")
    public String welcome(ModelMap map){
        map.put("teacherName",this.teacherService.getTeacherName());
        map.put("yblaas",this.publicService.getYblaas());
        map.put("leaveDsh",this.teacherService.getLeaveByDxjNum());
        map.put("studentDsh",this.teacherService.getStudentDshNum());
        return "teacher/welcome";
    }

    /**
     * 功能描述:
     * (老师首页)
     *
     * @param map 传参
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/3/31 23:43
     */
    @RequestMapping("index")
    public String index(ModelMap map){
        map.put("teacherName",this.teacherService.getTeacherName());
        map.put("roles", this.teacherService.getTeacherRoles());
        map.put("yblaas",this.publicService.getYblaas());
        return "teacher/index";
    }

    /**
     * 功能描述:
     * (个人信息页面)
     *
     * @param map 传参
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/3/31 23:43
     */
    @RequestMapping("my_data")
    public String my_data(ModelMap map){
        map.put("teacher", this.teacherService.getTeacherData());
        map.put("roles", this.teacherService.getTeacherRoles());
        return "teacher/my/my_data";
    }

    /**
     * 功能描述:
     * (老师绑定QQ)
     *
     * @param teacherQq 老师QQ
     * @param verification 验证码
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/3/31 23:43
     */
    @RequestMapping(value = "my_data_qq_ajax", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String my_data_qq_ajax(String teacherQq, String verification){
        return this.teacherService.changeTeacherQq(teacherQq, verification);
    }

    /**
     * 功能描述:
     * (老师绑定邮箱)
     *
     * @param teacherEmail 老师邮箱
     * @param verification 验证码
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/3/31 23:44
     */
    @RequestMapping(value = "my_data_email_ajax", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String my_data_email_ajax(String teacherEmail, String verification){
        return this.teacherService.changeTeacherEmail(teacherEmail, verification);
    }

    /**
     * 功能描述:
     * (老师取消绑定邮箱)
     *
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/5/15 17:02
     */
    @RequestMapping(value = "my_data_cancel_email_ajax", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String my_data_cancel_email_ajax(){
        return this.teacherService.cancelTeacherEmail();
    }

    /**
     * 功能描述:
     * (老师更新个人信息)
     *
     * @param teacher 老师实体类
     * @param verification 验证码
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/4/1 0:08
     */
    @RequestMapping(value = "my_data_ajax", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String my_data_ajax(Teacher teacher, String verification){
        return this.teacherService.changeTeacher(teacher, verification);
    }

    /**
     * 功能描述:
     * (假条查询页面)
     *
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/4/1 18:58
     */
    @RequestMapping("leave_get_leave_number_id")
    public String leave_get_leave_number_id(){
        return "teacher/leave/get_student_leave";
    }

    /**
     * 功能描述:
     * (假条查询ajax请求)
     *
     * @param numberId 学生的学号
     * @return : java.lang.Object
     * @author : xiaozhu
     * @date : 2020/4/1 18:59
     */
    @RequestMapping(value = "leave_get_leave_number_id_ajax", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object leave_get_leave_number_id_ajax(String numberId){
        LeaveTeacherQuery leaveByNumberId = this.teacherService.getLeaveByNumberId(numberId);
        //因为不是List对象所以需要封装一下
       if(leaveByNumberId == null){
            JSONObject jsonObject = new JSONObject();
            return jsonObject.toString();
        }else{
            JSONArray jsonArray = new JSONArray();
            jsonArray.add(leaveByNumberId);
            return jsonArray.toString();
        }
    }

    /**
     * 功能描述:
     * (假条的审核流程查询)
     *
     * @param leaveId 假条Id
     * @param map 返参
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/4/2 9:44
     */
    @RequestMapping("leave_get_leave_number_id_step")
    public String leave_get_leave_number_id_step(Long leaveId, ModelMap map){
        map.put("leaveTeacherStep", this.teacherService.getLeaveStepById(leaveId));
        return "teacher/leave/get_student_leave_step";
    }

    /**
     * 功能描述:
     * (老师待审核页面)
     *
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/4/2 21:49
     */
    @RequestMapping("leave_dsh")
    public String leave_dsh(){
        return "teacher/leave/leave_dsh";
    }

    /**
     * 功能描述:
     * (老师待审核页面的ajax请求)     *
     * @return : java.lang.Object
     * @author : xiaozhu
     * @date : 2020/4/2 21:49
     */
    @RequestMapping(value = "leave_dsh_ajax", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object leave_dsh_ajax(){
        return this.teacherService.getLeaveByDsh();
    }

    /**
     * 功能描述:
     * (老师通过假条审核)
     *
     * @param leaveId 假条Id
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/4/2 22:32
     */
    @RequestMapping(value = "leave_dsh_success_ajax", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String leave_dsh_success_ajax(Long leaveId){
        return this.teacherService.changeLeaveStateDsh(leaveId, true);
    }

    /**
     * 功能描述:
     * (老师不通过审核)
     *
     * @param leaveId 假条Id
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/4/2 22:33
     */
    @RequestMapping(value = "leave_dsh_error_ajax", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String leave_dsh_error_ajax(Long leaveId){
        return this.teacherService.changeLeaveStateDsh(leaveId, false);
    }

    /**
     * 功能描述:
     * (待销假页面)
     *
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/4/3 12:10
     */
    @RequestMapping("leave_dxj")
    public String leave_dxj(){
        return "teacher/leave/leave_dxj";
    }

    /**
     * 功能描述:
     * (待销假数据查询)
     *
     * @return : java.lang.Object
     * @author : xiaozhu
     * @date : 2020/4/3 12:11
     */
    @RequestMapping(value = "leave_dxj_ajax", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object leave_dxj_ajax(){
        return this.teacherService.getLeaveByDxj();
    }

    /**
     * 功能描述:
     * (对假条进行销假)
     *
     * @param leaveId 假条Id
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/4/3 13:13
     */
    @RequestMapping(value = "leave_dxj_success_ajax", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String leave_dxj_success_ajax(Long leaveId){
        return this.teacherService.changeLeaveStateDxj(leaveId);
    }

    /**
     * 功能描述:
     * (查询学生信息)
     *
     * @param leaveId 假条ID
     * @param map 返回页面参数
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/4/3 20:42
     */
    @RequestMapping("leave_student_data")
    public String leave_student_data(Long leaveId, ModelMap map){
        map.put("student", this.teacherService.getStudentData(leaveId));
        return "teacher/leave/student_data";
    }

    /**
     * 功能描述:
     * (老师全部假条页面)
     *
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/4/3 23:40
     */
    @RequestMapping("leave_all")
    public String leave_all(){
        return "teacher/leave/leave_all";
    }

    /**
     * 功能描述:
     * (老师查询全部假条ajax)
     *
     * @param draw datatables标识参数
     * @param length 每页长度
     * @param start 开始标记
     * @param column 排序的列序号
     * @param dir 排序方式
     * @param type 搜索方式
     * @param seachVar 搜索字段
     * @return : java.lang.Object
     * @author : xiaozhu
     * @date : 2020/4/3 23:40
     */
    @RequestMapping(value = "leave_all_ajax", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object leave_all_ajax(Integer draw, Integer length, Integer start, Integer column, String dir,Integer type, String seachVar){
        return this.teacherService.getLeaveAll(draw, length, start, column, dir, type, seachVar);
    }

    /**
     * 功能描述:
     * (发起考勤页面)
     *
     * @param map 返回页面参数
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/4/5 14:55
     */
    @RequestMapping("attendance_add")
    public String attendance_add(ModelMap map){
        map.put("accuracy",this.teacherService.getDbConfigAttendanceAccuracy());
        map.put("gaodeKey", this.publicService.getGaodeKey());
        return "teacher/attendance/attendance_add";
    }

    /**
     * 功能描述:
     * (提交考勤的ajax请求)
     *
     * @param attendance 考勤的实体类
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/4/5 17:56
     */
    @RequestMapping(value = "attendance_add_ajax", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String attendance_add_ajax(Attendance attendance){
        return this.teacherService.newAttendance(attendance);
    }

    /**
     * 功能描述:
     * (老师全部考勤页面)
     *
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/4/5 20:20
     */
    @RequestMapping("attendance_all")
    public String attendance_all(){
        return "teacher/attendance/attendance_all";
    }

    /**
     * 功能描述:
     * (老师全部考勤ajax查询)
     *
     * @param draw datatables标识
     * @param length 单页数量
     * @param start 开始数
     * @param column 排序列序号
     * @param dir 排序方式
     * @param type 搜索序号
     * @param seachVar 搜索字段
     * @return : java.lang.Object
     * @author : xiaozhu
     * @date : 2020/4/6 10:48
     */
    @RequestMapping(value = "attendance_all_ajax", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object attendance_all_ajax(Integer draw, Integer length, Integer start, Integer column, String dir,Integer type, String seachVar){
        return this.teacherService.getAttendanceAll(draw, length, start, column, dir, type, seachVar);
    }

    /**
     * 功能描述:
     * (考勤结果页面)
     *
     * @param attendanceId 考勤ID
     * @param map 返回页面参数
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/4/6 13:01
     */
    @RequestMapping("attendance_info")
    public String attendance_info(Long attendanceId, ModelMap map){
        map.put("attendanceInfoData", this.teacherService.getAttendanceInfoData(attendanceId));
        map.put("attendanceId", attendanceId);
        map.put("gaodeKey", this.publicService.getGaodeKey());
        return "teacher/attendance/attendance_info";
    }

    /**
     * 功能描述:
     * (考勤结果列表ajax)
     *
     * @param attendanceId 考勤ID
     * @return : java.lang.Object
     * @author : xiaozhu
     * @date : 2020/4/6 13:02
     */
    @RequestMapping(value = "attendance_info_ajax", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object attendance_info_ajax(Long attendanceId){
        return this.teacherService.getAttendanceInfoById(attendanceId);
    }

    /**
     * 功能描述:
     * (老师协助学生考勤)
     *
     * @param attendanceId 考勤ID
     * @param studentId 学生ID
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/4/6 13:04
     */
    @RequestMapping(value = "attendance_info_add_ajax", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String attendance_info_add_ajax(Long attendanceId, String studentId){
        return this.teacherService.addAttendanceInfo(attendanceId, studentId);
    }

    /**
     * 功能描述:
     * (老师取消学生考勤)
     *
     * @param attendanceId 考勤ID
     * @param studentId 学生ID
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/4/6 13:04
     */
    @RequestMapping(value = "attendance_info_del_ajax", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String attendance_info_del_ajax(Long attendanceId, String studentId){
        return this.teacherService.delAttendanceInfo(attendanceId, studentId);
    }

    /**
     * 功能描述:
     * (待考勤页面)
     *
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/4/6 19:08
     */
    @RequestMapping("attendance_dkq")
    public String attendance_dkq(){
        return "teacher/attendance/attendance_dkq";
    }

    /**
     * 功能描述:
     * (待考勤数据ajax请求)
     *
     * @return : java.lang.Object
     * @author : xiaozhu
     * @date : 2020/4/6 19:08
     */
    @RequestMapping(value = "attendance_dkq_ajax", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object attendance_dkq_ajax(){
        return this.teacherService.getAttendanceDkq();
    }

    /**
     * 功能描述:
     * (停止考勤ajax请求)
     *
     * @param attendanceId 考勤ID
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/4/6 19:08
     */
    @RequestMapping(value = "attendance_dkq_stop_ajax", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String attendance_dkq_stop_ajax(Long attendanceId){
        return this.teacherService.changeAttendanceStop(attendanceId);
    }

    /**
     * 功能描述:
     * (启用考勤ajax请求)
     *
     * @param attendanceId 考勤ID
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/4/6 19:09
     */
    @RequestMapping(value = "attendance_dkq_enable_ajax", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String attendance_dkq_enable_ajax(Long attendanceId){
        return this.teacherService.changeAttendanceEnable(attendanceId);
    }

    /**
     * 功能描述:
     * (删除考勤ajax请求)
     *
     * @param attendanceId 考勤ID
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/4/6 19:09
     */
    @RequestMapping(value = "attendance_dkq_del_ajax", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String attendance_dkq_del_ajax(Long attendanceId){
        return this.teacherService.changeAttendanceDel(attendanceId);
    }

    /**
     * 功能描述:
     * (学生信息待审核页面)
     *
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/4/11 14:59
     */
    @RequiresRoles("fdy")
    @RequestMapping("user_student_dsh")
    public String user_student_dsh(){
        return "teacher/user/student_dsh";
    }

    /**
     * 功能描述:
     * (学生信息待审核数据请求)
     *
     * @return : java.lang.Object
     * @author : xiaozhu
     * @date : 2020/4/11 14:59
     */
    @RequiresRoles("fdy")
    @RequestMapping(value = "user_student_dsh_ajax", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object user_student_dsh_ajax(){
        return this.teacherService.getStudentListDsh();
    }

    /**
     * 功能描述:
     * (学生信息审核通过)
     *
     * @param studentId 学生Id
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/4/11 16:14
     */
    @RequiresRoles("fdy")
    @RequestMapping(value = "user_student_dsh_success_ajax", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String user_student_dsh_success_ajax(String studentId){
        return this.teacherService.changeStudentExamine(studentId, "2");
    }

    /**
     * 功能描述:
     * (学生信息审核不通过)
     *
     * @param studentId 学生ID
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/4/11 16:14
     */
    @RequiresRoles("fdy")
    @RequestMapping(value = "user_student_dsh_error_ajax", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String user_student_dsh_error_ajax(String studentId){
        return this.teacherService.changeStudentExamine(studentId, "3");
    }

    /**
     * 功能描述:
     * (学生信息批量审核通过)
     *
     * @param studentIds 学生Ids
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/4/11 16:14
     */
    @RequiresRoles("fdy")
    @RequestMapping(value = "user_student_dsh_success_list_ajax", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String user_student_dsh_success_list_ajax(@RequestParam(value = "studentIds[]")String[] studentIds){
        return this.teacherService.changeStudentExamines(studentIds);
    }

    /**
     * 功能描述:
     * (辅导员班级查询页面)
     *
     * @return : java.lang.Object
     * @author : xiaozhu
     * @date : 2020/4/12 13:01
     */
    @RequiresRoles("fdy")
    @RequestMapping("user_eclass_fdy")
    public Object user_eclass_fdy(){
        return "teacher/user/eclass_fdy";
    }

    /**
     * 功能描述:
     * (辅导员班级查询ajax请求)
     *
     * @return : java.lang.Object
     * @author : xiaozhu
     * @date : 2020/4/12 13:01
     */
    @RequiresRoles("fdy")
    @RequestMapping(value = "user_eclass_fdy_ajax", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object user_eclass_fdy_ajax(){
        return this.teacherService.getEclassTeacherByTeacherId();
    }

    /**
     * 功能描述:
     * (辅导员班级详情页面)
     *
     * @param map 前台参数
     * @param eclassId 班级Id
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/4/12 13:02
     */
    @RequiresRoles("fdy")
    @RequestMapping("user_eclass_info")
    public String user_eclass_info(ModelMap map, Integer eclassId){
        map.put("eclassId",eclassId);
        return "teacher/user/eclass_info";
    }

    /**
     * 功能描述:
     * (班级学生信息ajax请求)
     *
     * @param eclassId 1
     * @return : java.lang.Object
     * @author : xiaozhu
     * @date : 2020/4/12 13:02
     */
    @RequiresRoles(value={"fdy","xyld"},logical= Logical.OR)
    @RequestMapping(value = "user_eclass_info_ajax", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object user_eclass_info_ajax(Integer eclassId){
        return this.teacherService.getTeacherStudentList(eclassId);
    }

    /**
     * 功能描述:
     * (辅导员班级删除学生)
     *
     * @param studentId 学生ID
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/4/12 13:03
     */
    @RequiresRoles("fdy")
    @RequestMapping(value = "user_eclass_info_del_ajax", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String user_eclass_info_del_ajax(String studentId){
        return this.teacherService.changeStudentExamine(studentId, "3");
    }

    /**
     * 功能描述:
     * (学院查看班级信息学生页面)
     *
     * @param map 返回页面参数
     * @param eclassId 班级ID
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/4/12 13:04
     */
    @RequiresRoles("xyld")
    @RequestMapping("user_eclass_info_xy")
    public String user_eclass_info_xy(ModelMap map, Integer eclassId){
        map.put("eclassId",eclassId);
        return "teacher/user/eclass_info_xy";
    }

    /**
     * 功能描述:
     * (学院班级管理页面)
     *
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/4/12 13:06
     */
    @RequiresRoles("xyld")
    @RequestMapping("user_eclass_xy")
    public String user_eclass_xy(){
        return "teacher/user/eclass_xy";
    }

    /**
     * 功能描述:
     * (学院班级管理获取数据)
     *
     * @return : java.lang.Object
     * @author : xiaozhu
     * @date : 2020/4/13 10:39
     */
    @RequiresRoles("xyld")
    @RequestMapping(value = "user_eclass_xy_ajax", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object user_eclass_xy_ajax(){
        return this.teacherService.getEclassTeacherByCollegeId();
    }

    /**
     * 功能描述:
     * (任命班级辅导员页面)
     *
     * @param map 页面参数
     * @param eclassId 班级ID
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/4/13 10:42
     */
    @RequiresRoles("xyld")
    @RequestMapping("user_eclass_teacher")
    public String user_eclass_teacher(ModelMap map, Integer eclassId){
        map.put("eclassId",eclassId);
        return "teacher/user/eclass_teacher";
    }

    /**
     * 功能描述:
     * (任命辅导员页面老师信息获取)
     *
     * @return : java.lang.Object
     * @author : xiaozhu
     * @date : 2020/4/13 10:45
     */
    @RequiresRoles("xyld")
    @RequestMapping(value = "user_eclass_teacher_ajax", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object user_eclass_teacher_ajax(){
        return this.teacherService.getTeacherByCollegeId();
    }

    /**
     * 功能描述:
     * (任命辅导员)
     *
     * @param eclassId 班级ID
     * @param teacherId 老师ID
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/4/13 12:14
     */
    @RequiresRoles("xyld")
    @RequestMapping(value = "user_eclass_teacher_add_ajax", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String user_eclass_teacher_add_ajax(Integer eclassId, String teacherId){
        return this.teacherService.changeEclassTeacherId(teacherId, eclassId);
    }

    /**
     * 功能描述:
     * (学院删除班级辅导员任命)
     *
     * @param eclassId 班级ID
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/4/13 12:20
     */
    @RequiresRoles("xyld")
    @RequestMapping(value = "user_eclass_teacher_del", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String user_eclass_teacher_del(Integer eclassId){
        return this.teacherService.delEclassTeacherId(eclassId);
    }

    /**
     * 功能描述:
     * (学院新增班级)
     *
     * @param eclassName 班级姓名
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/4/14 10:00
     */
    @RequiresRoles("xyld")
    @RequestMapping(value = "user_eclass_xy_add", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String user_eclass_xy_add(String eclassName){
        return this.teacherService.addEclass(eclassName);
    }

    /**
     * 功能描述:
     * (修改班级名称)
     *
     * @param eclassName 班级名称
     * @param eclassId 班级ID
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/4/14 10:49
     */
    @RequiresRoles("xyld")
    @RequestMapping(value = "user_eclass_xy_change", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String user_eclass_xy_change(String eclassName, Integer eclassId){
        return this.teacherService.changeEclassName(eclassName, eclassId);
    }

    /**
     * 功能描述:
     * (学院删除班级)
     *
     * @param eclassId 班级ID
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/4/14 16:23
     */
    @RequiresRoles("xyld")
    @RequestMapping(value = "user_eclass_xy_del", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String user_eclass_xy_del(Integer eclassId){
        return this.teacherService.delEclass(eclassId);
    }

    /**
     * 功能描述:
     * (学工处学院管理页面)
     *
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/4/14 16:23
     */
    @RequiresRoles("xgc")
    @RequestMapping("user_college")
    public String user_college(){
        return "teacher/user/college";
    }

    /**
     * 功能描述:
     * (学工处查询学院老师页面)
     *
     * @param collegeId 学院ID
     * @param map 页面参数
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/4/14 16:24
     */
    @RequiresRoles("xgc")
    @RequestMapping("user_college_teacher")
    public String user_college_teacher(Integer collegeId,ModelMap map){
        map.put("collegeId",collegeId);
        return "teacher/user/college_teacher";
    }

    /**
     * 功能描述:
     * (学工处获取学院老师信息)
     *
     * @param collegeId 学院ID
     * @return : java.lang.Object
     * @author : xiaozhu
     * @date : 2020/4/14 16:24
     */
    @RequiresRoles("xgc")
    @RequestMapping(value = "user_college_teacher_ajax", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object user_college_teacher_ajax(Integer collegeId){
        return this.teacherService.getTeacherXyld(collegeId);
    }

    /**
     * 功能描述:
     * (学工处任命学院老师为“学院”角色)
     *
     * @param teacherId 老师ID
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/4/14 16:24
     */
    @RequiresRoles("xgc")
    @RequestMapping(value = "user_college_teacher_xyld_ajax", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String user_college_teacher_xyld_ajax(String teacherId){
        return this.teacherService.changeTeacherXyldRole(teacherId,"xyld");
    }

    /**
     * 功能描述:
     * (学工处取消任命学院老师“学院”角色)
     *
     * @param teacherId 老师ID
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/4/14 16:25
     */
    @RequiresRoles("xgc")
    @RequestMapping(value = "user_college_teacher_teacher_ajax", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String user_college_teacher_teacher_ajax(String teacherId){
        return this.teacherService.changeTeacherXyldRole(teacherId,"teacher");
    }

    /**
     * 功能描述:
     * (学工处添加学院)
     *
     * @param collegeName 学院名称
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/4/14 20:18
     */
    @RequiresRoles("xgc")
    @RequestMapping(value = "user_college_add_ajax", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String user_college_add_ajax(String collegeName){
        return this.teacherService.addCollege(collegeName);
    }

    /**
     * 功能描述:
     * (学工处修改学院名称)
     *
     * @param collegeName 学院名称
     * @param collegeId 学院ID
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/4/14 20:18
     */
    @RequiresRoles("xgc")
    @RequestMapping(value = "user_college_change_ajax", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String user_college_change_ajax(String collegeName, Integer collegeId){
        return this.teacherService.changeCollege(collegeName, collegeId);
    }

    /**
     * 功能描述:
     * (学工处删除学院)
     *
     * @param collegeId 学院ID
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/4/14 20:18
     */
    @RequiresRoles("xgc")
    @RequestMapping(value = "user_college_del_ajax", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String user_college_del_ajax(Integer collegeId){
        return this.teacherService.delCollege(collegeId);
    }

    /**
     * 功能描述:
     * (老师退出系统)
     *
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/4/14 21:50
     */
    @RequiresUser
    @RequestMapping("exit")
    public String exit(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/public/user_exit";
    }
}
