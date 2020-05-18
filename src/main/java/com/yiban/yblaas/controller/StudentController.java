package com.yiban.yblaas.controller;

import com.yiban.yblaas.domain.AttendanceInfo;
import com.yiban.yblaas.domain.Eclass;
import com.yiban.yblaas.domain.NewLeave;
import com.yiban.yblaas.domain.Student;
import com.yiban.yblaas.service.impl.PublicServiceImpl;
import com.yiban.yblaas.service.impl.StudentServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: yblaas
 * @description: 学生Controller
 * @author: xiaozhu
 * @create: 2020-03-21 17:08
 **/
@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentServiceImpl studentService;
    @Autowired
    private PublicServiceImpl publicService;

    /**
     * 功能描述:
     * (学生首页)
     *
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/3/21 17:10
     */
    @RequiresPermissions("student:leave")
    @RequestMapping("index")
    public String index(ModelMap map){
        map.put("yblaas",this.publicService.getYblaas());
        return "student/index";
    }

    /**
     * 功能描述:
     * (学生假条信息的ajax请求)
     *
     * @param start 开始页数
     * @param length 每页的数目
     * @param number 1-全部 2-待审核 3-待销假
     * @return : java.lang.Object
     * @author : xiaozhu
     * @date : 2020/3/22 11:38
     */
    @RequiresPermissions("student:leave")
    @RequestMapping(value = "index_ajax",  produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object index_ajax(Integer start, Integer length, Integer number){
        return this.studentService.getStudentLeave(start, length, number);
    }

    /**
     * 功能描述:
     * (学生进入申请假条页面)
     *
     * @param map 传递参数map
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/3/22 18:58
     */
    @RequiresPermissions("student:leave")
    @RequestMapping("leave_new_leave")
    public String leave_new_leave(ModelMap map){
        if(this.studentService.newLeaveView()){
            map.put("studentName",this.studentService.getstudentName());
            return "student/leave/new_leave";
        }else{
            return "redirect:/student/leave_new_leave_error";
        }
    }

    /**
     * 功能描述:
     * (学生提交假条申请)
     *
     * @param newLeave 新假条的实体类
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/3/23 0:13
     */
    @RequiresPermissions("student:leave")
    @RequestMapping(value = "leave_new_leave_ajax", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String leave_new_leave_ajax(NewLeave newLeave){
        if(this.studentService.newLeaveView()){
            return this.studentService.addNewLeave(newLeave);
        }else{
            return "error";
        }
    }

    /**
     * 功能描述:
     * (学生申请假条成功页面)
     *
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/3/23 0:14
     */
    @RequiresPermissions("student:leave")
    @RequestMapping("leave_new_leave_error")
    public String leave_new_leave_error(ModelMap map){
        map.put("yblaas",this.publicService.getYblaas());
        return "student/static/new_leave_error";
    }

    /**
     * 功能描述:
     * (学生个人信息未认证的页面)
     *
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/3/23 0:14
     */
    @RequiresPermissions("student:leave")
    @RequestMapping("leave_new_leave_success")
    public String leave_new_leave_success(ModelMap map){
        map.put("yblaas",this.publicService.getYblaas());
        return "student/static/new_leave_success";
    }

    /**
     * 功能描述:
     * (学生查询假条的详情)
     *
     * @param leaveId 假条Id
     * @param map 返回页面的参数
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/3/23 20:21
     */
    @RequiresPermissions("student:leave")
    @RequestMapping("leave_query_leave")
    public String leave_query_leave(Long leaveId, ModelMap map){
        map.put("leave",this.studentService.getLeaveById(leaveId));
        return "student/leave/leave_query";
    }

    /**
     * 功能描述:
     * (学生我的页面)
     *
     * @param map 1
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/3/24 18:40
     */
    @RequiresPermissions("student:my")
    @RequestMapping("my_index")
    public String my_index(ModelMap map){
        map.put("examine", this.studentService.getStudentExamine());
        map.put("studentName", this.studentService.getstudentName());
        map.put("studentQq", this.studentService.getStudentQq());
        map.put("studentEmail", this.studentService.getStudentEmail());
        return "student/my/index";
    }

    /**
     * 功能描述:
     * (学生个人信息页)
     *
     * @param map 返回页面参数
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/3/28 20:30
     */
    @RequiresPermissions("student:my")
    @RequestMapping("my_data")
    public String my_data(ModelMap map){
        Student student = this.studentService.getStudent();
        Eclass eclass = this.studentService.getEclassByEclassId(student.getEclassId());
        if(eclass != null){
            map.put("eclass", eclass);
            map.put("collegeName", this.studentService.getCollegeName(eclass.getEclassId()));
        }
        map.put("student", student);
        return "student/my/my_data";
    }

    /**
     * 功能描述:
     * (个人信息修改提交)
     *
     * @param student 学生实体类
     * @param verification 手机验证码
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/3/28 20:31
     */
    @RequiresPermissions("student:my")
    @RequestMapping(value = "my_data_ajax", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String my_data_ajax(Student student,String verification){
        return this.studentService.changeStudentData(student,verification);
    }

    /**
     * 功能描述:
     * (学生个人信息提交成功页)
     *
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/3/28 20:31
     */
    @RequiresPermissions("student:my")
    @RequestMapping("my_data_success")
    public String my_data_success(ModelMap map){
        map.put("yblaas",this.publicService.getYblaas());
        return "student/static/my_data_success";
    }

    /**
     * 功能描述:
     * (学生绑定QQ页)
     *
     * @param map 返回页面参数
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/3/28 20:32
     */
    @RequiresPermissions("student:my")
    @RequestMapping("my_data_qq")
    public String my_data_qq(ModelMap map){
        map.put("studentQq", this.studentService.getStudentQq());
        return "student/my/my_qq";
    }

    /**
     * 功能描述:
     * (学生提交绑定QQ)
     *
     * @param studentQq 学生QQ
     * @param verification QQ验证码
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/3/28 20:32
     */
    @RequiresPermissions("student:my")
    @RequestMapping(value = "my_data_qq_ajax", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String my_data_qq_ajax(String studentQq,String verification){
        return this.studentService.changeStudentQq(studentQq, verification);
    }

    /**
     * 功能描述:
     * (学生绑定邮箱页)
     *
     * @param map 返回页面参数
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/3/28 20:32
     */
    @RequiresPermissions("student:my")
    @RequestMapping("my_data_email")
    public String my_data_email(ModelMap map){
        map.put("studentEmail", this.studentService.getStudentEmail());
        return "student/my/my_email";
    }

    /**
     * 功能描述:
     * (学生提交邮箱绑定)
     *
     * @param studentEmail 学生邮箱
     * @param verification 邮箱验证码
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/3/28 20:33
     */
    @RequiresPermissions("student:my")
    @RequestMapping(value = "my_data_email_ajax", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String my_data_email_ajax(String studentEmail, String verification){
        return this.studentService.changeStudentEmail(studentEmail, verification);
    }

    /**
     * 功能描述:
     * (学生取消邮箱绑定)
     *
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/5/15 16:47
     */
    @RequiresPermissions("student:my")
    @RequestMapping(value = "my_data_cancel_email_ajax", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String my_data_cancel_email_ajax(){
        return this.studentService.cancelStudentEmail();
    }

    /**
     * 功能描述:
     * (学生考勤页面)
     *
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/4/6 22:40
     */
    @RequiresPermissions("student:attendance")
    @RequestMapping("attendance_index")
    public String attendance_index(){
        return "student/attendance/index";
    }

    /**
     * 功能描述:
     * (学生考勤信息ajax)
     *
     * @param start 开始数
     * @param length 每页数
     * @param number 类型 1-待考勤 2-全部
     * @return : java.lang.Object
     * @author : xiaozhu
     * @date : 2020/4/7 15:57
     */
    @RequiresPermissions("student:attendance")
    @RequestMapping(value = "attendance_index_ajax",  produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object attendance_index_ajax(Integer start, Integer length, Integer number){
        return this.studentService.getStudentAttendance(start, length, number);
    }

    /**
     * 功能描述:
     * (考勤详情页面)
     *
     * @param attendanceId 考勤ID
     * @param map 返回页面参数
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/4/7 15:58
     */
    @RequiresPermissions("student:attendance")
    @RequestMapping("attendance_info")
    public String attendance_info(Long attendanceId, ModelMap map){
        map.put("attendance", this.studentService.getAttendance(attendanceId));
        map.put("attendanceTime", this.studentService.getAttendanceInfoTime(attendanceId));
        return "student/attendance/attendance_info";
    }

    /**
     * 功能描述:
     * (学生提交定位考勤)
     *
     * @param attendanceInfo 考勤信息实体类
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/4/7 20:20
     */
    @RequiresPermissions("student:attendance")
    @RequestMapping(value = "attendance_info_location_ajax", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String attendance_info_location_ajax(AttendanceInfo attendanceInfo){
        return this.studentService.addAttendanceInfo(attendanceInfo);
    }

    /**
     * 功能描述:
     * (学生成功提交考勤)
     *
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/4/7 20:20
     */
    @RequiresPermissions("student:attendance")
    @RequestMapping("attendance_info_success")
    public String attendance_info_success(ModelMap map){
        map.put("yblaas",this.publicService.getYblaas());
        return "student/static/attendance_info_success";
    }

    /**
     * 功能描述:
     * (学生定位考勤页面)
     *
     * @param attendanceId 假条ID
     * @param map 返回页面参数
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/4/7 20:20
     */
    @RequiresPermissions("student:attendance")
    @RequestMapping("attendance_info_location")
    public String attendance_info_location(Long attendanceId, ModelMap map){
        map.put("attendance", this.studentService.getAttendance(attendanceId));
        map.put("accuracy",this.studentService.getDbConfigAttendanceAccuracy());
        return "student/attendance/attendance_info_location";
    }

    /**
     * 功能描述:
     * (学生退出系统)
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

    /**
     * 功能描述:
     * (学生取消假条申请)
     *
     * @param leaveId 假条ID
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/4/24 15:21
     */
    @RequiresPermissions("student:leave")
    @RequestMapping("cancel_leave")
    public String cancel_leave(Long leaveId){
        if("success".equals(this.studentService.changeCancelLeave(leaveId))){
            return "redirect:/student/cancel_leave_success";
        }else{
            return "redirect:/student/cancel_leave_error";
        }
    }

    /**
     * 功能描述:
     * (取消假条成功)
     *
     * @param map 返回页面参数
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/4/24 15:18
     */
    @RequiresPermissions("student:leave")
    @RequestMapping("cancel_leave_success")
    public String cancel_leave_success(ModelMap map){
        map.put("yblaas",this.publicService.getYblaas());
        return "student/static/cancel_leave_success";
    }

    /**
     * 功能描述:
     * (取消假条失败)
     *
     * @param map 返回页面参数
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/4/24 15:19
     */
    @RequiresPermissions("student:leave")
    @RequestMapping("cancel_leave_error")
    public String cancel_leave_error(ModelMap map){
        map.put("yblaas",this.publicService.getYblaas());
        return "student/static/cancel_leave_error";
    }
}
