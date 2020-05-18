package com.yiban.yblaas.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yiban.yblaas.domain.*;
import com.yiban.yblaas.mapper.*;
import com.yiban.yblaas.service.StudentService;
import com.yiban.yblaas.util.DateUtil;
import com.yiban.yblaas.util.MessageUtil;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @program: yblaas
 * @description: 学生接口类的实现
 * @author: xiaozhu
 * @create: 2020-03-21 17:33
 **/
@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Autowired
    private LeaveMapper leaveMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private EclassMapper eclassMapper;
    @Autowired
    private CollegeMapper collegeMapper;
    @Autowired
    private VerificationMapper verificationMapper;
    @Autowired
    private AttendanceMapper attendanceMapper;
    @Autowired
    private AttendanceInfoMapper attendanceInfoMapper;
    @Autowired
    private DbConfigMapper dbConfigMapper;
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private MessageUtil messageUtil;

    /**
     * 功能描述:
     * (学生查询假条)
     *
     * @param start 开始页数
     * @param length 单页条数
     * @param number 1-全部 2-待审核 3-销假
     * @return : com.yiban.yblaas.domain.DataTables<com.yiban.yblaas.domain.LeaveStudent>
     * @author : xiaozhu
     * @date : 2020/3/22 11:37
     */
    @Override
    public DataTables<LeaveStudent> getStudentLeave(Integer start, Integer length, Integer number) {
        try {
            String studentId = SecurityUtils.getSubject().getPrincipal().toString();
            PageHelper.startPage(start,length);
            List<LeaveStudent> leaveStudents = this.leaveMapper.selectLeaveByStudentId(studentId,number);
            PageInfo<LeaveStudent> pageInfo = new PageInfo<LeaveStudent>(leaveStudents);
            return new DataTables(null, (long) pageInfo.getTotal(), null, pageInfo.getList(), null);
        } catch (Exception e) {
            logger.error("学生查询假条错误，错误信息："+e.toString());
            return new DataTables(null, (long) 0, null, null, null);
        }
    }

    /**
     * 功能描述:
     * (查询学生信息审核是否通过)
     *
     * @return : java.lang.Boolean
     * @author : xiaozhu
     * @date : 2020/3/22 18:51
     */
    @Override
    public Boolean newLeaveView() {
        try {
            String studentId = (String) SecurityUtils.getSubject().getPrincipal();
            if("2".equals(this.studentMapper.selectStudentExamine(studentId))){
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            logger.error("查询学生信息审核是否通过错误，错误信息："+e.toString());
            return false;
        }
    }

    /**
     * 功能描述:
     * (查询学生姓名)
     *
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/3/22 18:54
     */
    @Override
    public String getstudentName() {
        try {
            String studentId = (String) SecurityUtils.getSubject().getPrincipal();
            return this.studentMapper.selectStudentName(studentId);
        } catch (Exception e) {
            logger.error("查询学生姓名错误，错误信息："+e.toString());
            return "demo";
        }
    }

    /**
     * 功能描述:
     * (学生申请请假)
     *
     * @param newLeave 新假条实体类
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/3/22 23:16
     */
    @Override
    public String addNewLeave(NewLeave newLeave) {
        try {
            String studentId = (String) SecurityUtils.getSubject().getPrincipal();
            Student student = this.studentMapper.selectStudent(studentId);
            newLeave.setStudent(studentId);
            int day = differentDays(newLeave.getTimeStart(), newLeave.getTimeEnd()) + 1;
            if(day>=1){
                newLeave.setDay(day);
                if(this.leaveMapper.insertLeave(newLeave) == 1){
                    //学生提醒消息
                    String yblaasTitle = this.dbConfigMapper.selectValue("yblaas_title");
                    messageUtil.Qq(student.getStudentQq(), yblaasTitle,1, newLeave.getId().toString());
                    messageUtil.Email(student.getStudentEmail(), yblaasTitle, 1, newLeave.getId().toString());
                    Eclass eclass = this.eclassMapper.selectEclass(student.getEclassId());
                    if(eclass.getTeacher()!=null){
                        //老师提醒消息
                        Teacher teacher = this.teacherMapper.selectTeacher(eclass.getTeacher());
                        messageUtil.Qq(teacher.getTeacherQq(), yblaasTitle,7, newLeave.getId().toString());
                        messageUtil.Email(teacher.getTeacherEmail(), yblaasTitle, 7, newLeave.getId().toString());
                    }
                    return "success";
                }else {
                    return "error";
                }
            }else{
                return "error_date";
            }
        } catch (Exception e) {
            logger.error("学生申请请假错误，错误信息："+e.toString());
            return "error";
        }
    }

    /**
     * 功能描述:
     * (学生查看假条的详细信息)
     *
     * @param leaveId 假条id
     * @return : com.yiban.yblaas.domain.LeaveStudentQuery
     * @author : xiaozhu
     * @date : 2020/3/23 13:49
     */
    @Override
    public LeaveStudentQuery getLeaveById(Long leaveId) {
        try {
            return this.leaveMapper.selectLeaveStudentQueryById(leaveId);
        } catch (Exception e) {
            logger.error("学生查看假条详细信息错误，错误信息："+e.toString());
            return null;
        }
    }

    /**
     * 功能描述:
     * (查询学生的个人信息审核情况)
     *
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/3/23 20:19
     */
    @Override
    public String getStudentExamine() {
        try {
            String studentId = (String) SecurityUtils.getSubject().getPrincipal();
            return this.studentMapper.selectStudentExamine(studentId);
        } catch (Exception e) {
            logger.error("查询学生的个人信息审核情况出错，错误信息："+e.toString());
            return null;
        }
    }

    /**
     * 功能描述:
     * (查询学生信息接口)
     *
     * @return : com.yiban.yblaas.domain.Student
     * @author : xiaozhu
     * @date : 2020/3/24 18:44
     */
    @Override
    public Student getStudent(){
        try {
            String studentId = (String) SecurityUtils.getSubject().getPrincipal();
            return this.studentMapper.selectStudent(studentId);
        } catch (Exception e) {
            logger.error("查询学生信息错误，错误信息："+e.toString());
            return null;
        }
    }

    @Override
    public Eclass getEclassByEclassId(Integer eclassId) {
        try {
            if(eclassId!= null && eclassId !=0){
                return this.eclassMapper.selectEclass(eclassId);
            }else{
                return null;
            }
        } catch (Exception e) {
            logger.error("学生根据班级ID查询班级信息错误，错误信息："+e.toString());
            return null;
        }
    }

    @Override
    public String getCollegeName(Integer collegeId) {
        try {
            if(collegeId!=null&&collegeId!=0){
                return this.collegeMapper.selectCollegeName(collegeId);
            }else{
                return null;
            }
        } catch (Exception e) {
            logger.error("学生查询学院名称错误，错误信息："+e.toString());
            return null;
        }
    }

    /**
     * 功能描述:
     * (学生更新个人信息)
     *
     * @param student 学生实体类
     * @param verification 验证码
     * @return : java.lang.String error1-个人信息待审核 error2-验证码不正确 error3-信息未修改
     * @author : xiaozhu
     * @date : 2020/3/26 10:51
     */
    @Override
    public String changeStudentData(Student student, String verification) {
        try {
            String studentId = (String) SecurityUtils.getSubject().getPrincipal();
            //个人资料待审核
            if("1".equals(this.studentMapper.selectStudentExamine(studentId))){
                return "error1";
            }
//            Verification verificationDomain = this.verificationMapper.selectVerification(studentId,"message");
//            if(verificationDomain!=null){
//                //验证码已经过期或者验证字段不对
//                if(DateUtil.getDifferSeconds(verificationDomain.getTime())>600){
//                    return "error2";
//                }
//            }
//            if(verification.equals(verificationDomain.getVerification())&&student.getStudentTell().equals(verificationDomain.getField())){
                student.setStudent(studentId);
                student.setExamine("1");
                if(this.studentMapper.updateStudent(student)==1){
                    return "success";
                }else{
                    return "error3";
                }
//            }else{
//                return "error2";
//            }
        } catch (Exception e) {
            logger.error("学生更新个人信息错误，错误信息："+e.toString());
            return "error2";
        }
    }

    /**
     * 功能描述:
     * (查询学生的绑定QQ)
     *
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/3/28 12:49
     */
    @Override
    public String getStudentQq() {
        try {
            String studentId = (String) SecurityUtils.getSubject().getPrincipal();
            return this.studentMapper.selectStudentQq(studentId);
        } catch (Exception e) {
            logger.error("查询学生的QQ错误，错误信息："+e.toString());
            return null;
        }
    }

    /**
     * 功能描述:
     * (查询学生绑定的邮箱)
     *
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/3/31 22:45
     */
    @Override
    public String getStudentEmail() {
        try {
            String studentId = (String) SecurityUtils.getSubject().getPrincipal();
            return this.studentMapper.selectStudentEmail(studentId);
        } catch (Exception e) {
            logger.error("查询学生的邮箱错误，错误信息："+e.toString());
            return null;
        }
    }

    /**
     * 功能描述:
     * (学生绑定QQ)
     *
     * @param studentQq 学生QQ
     * @param verification 验证码
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/3/31 22:57
     */
    @Override
    public String changeStudentQq(String studentQq, String verification) {
        try {
            String studentId = (String) SecurityUtils.getSubject().getPrincipal();
            Verification verificationDomain = this.verificationMapper.selectVerification(studentId,"qq");
            if(verificationDomain!=null){
                //验证码已经过期或者验证字段不对 半个钟有效期
                if(DateUtil.getDifferSeconds(verificationDomain.getTime())>1800){
                    return "error";
                }
            }
            if(verification.equals(verificationDomain.getVerification())&&studentQq.equals(verificationDomain.getField())){
                if(this.studentMapper.updateStudentQq(studentId, studentQq)==1){
                    return "success";
                }else{
                    return "error";
                }
            }else{
                return "error";
            }
        } catch (Exception e) {
            logger.error("学生绑定QQ错误，错误信息："+e.toString());
            return "error";
        }
    }

    /**
     * 功能描述:
     * (学生绑定邮箱)
     *
     * @param studentEmail 学生邮箱
     * @param verification 验证码
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/3/31 22:58
     */
    @Override
    public String changeStudentEmail(String studentEmail, String verification) {
        try {
            String studentId = (String) SecurityUtils.getSubject().getPrincipal();
            Verification verificationDomain = this.verificationMapper.selectVerification(studentId,"email");
            if(verificationDomain!=null){
                //验证码已经过期或者验证字段不对 半个钟有效期
                if(DateUtil.getDifferSeconds(verificationDomain.getTime())>1800){
                    return "error";
                }
            }
            if(verification.equals(verificationDomain.getVerification())&&studentEmail.equals(verificationDomain.getField())){
                if(this.studentMapper.updateStudentEmail(studentId, studentEmail)==1){
                    return "success";
                }else{
                    return "error";
                }
            }else{
                return "error";
            }
        } catch (Exception e) {
            logger.error("学生绑定邮箱错误，错误信息："+e.toString());
            return "error";
        }
    }

    /**
     * 功能描述:
     * (学生查询考勤信息)
     *
     * @param start 开始数
     * @param length 每页数
     * @param number 1-待考勤 2-全部考勤
     * @return : com.yiban.yblaas.domain.DataTables<com.yiban.yblaas.domain.AttendanceStudent>
     * @author : xiaozhu
     * @date : 2020/4/6 22:32
     */
    @Override
    public DataTables<AttendanceStudent> getStudentAttendance(Integer start, Integer length, Integer number) {
        try {
            String studentId = SecurityUtils.getSubject().getPrincipal().toString();
            Student student = this.studentMapper.selectStudent(studentId);
            List<AttendanceStudent> attendanceStudents = null;
            if(student != null){
                if(student.getExamine().equals("2")){
                    if(student.getEclassId() != null && student.getEclassId() != 0){
                        if(number == 1){
                            PageHelper.startPage(start,length);
                            attendanceStudents = this.attendanceMapper.selectAttendanceStudentDkq(student.getEclassId(), studentId);
                        }
                        if(number == 2){
                            PageHelper.startPage(start,length);
                            attendanceStudents = this.attendanceMapper.selectAttendanceStudentAll(student.getEclassId());
                        }
                    }
                }
            }
            if(attendanceStudents == null){
                return new DataTables(null, (long) 0, null, null, null);
            }else{
                PageInfo<AttendanceStudent> pageInfo = new PageInfo<AttendanceStudent>(attendanceStudents);
                return new DataTables(null, (long) pageInfo.getTotal(), null, pageInfo.getList(), null);
            }
        } catch (Exception e) {
            logger.error("学生查询考勤信息错误，错误信息："+e.toString());
            return new DataTables(null, (long) 0, null, null, null);
        }
    }

    /**
     * 功能描述:
     * (学生查询考勤信息)
     *
     * @param attendanceId 考勤ID
     * @return : com.yiban.yblaas.domain.Attendance
     * @author : xiaozhu
     * @date : 2020/4/7 10:24
     */
    @Override
    public Attendance getAttendance(Long attendanceId) {
        try {
            return this.attendanceMapper.selectAttendanceByAttendanceId(attendanceId);
        } catch (Exception e) {
            logger.error("学生查询考勤信息错误，错误信息："+e.toString());
            return null;
        }
    }

    /**
     * 功能描述:
     * (学生查询考勤时间)
     *
     * @param attendanceId 考勤ID
     * @return : java.util.Date
     * @author : xiaozhu
     * @date : 2020/4/7 10:27
     */
    @Override
    public Date getAttendanceInfoTime(Long attendanceId) {
        try {
            String studentId = SecurityUtils.getSubject().getPrincipal().toString();
            return this.attendanceInfoMapper.selectAttendanceInfoTime(attendanceId, studentId);
        } catch (Exception e) {
            logger.error("学生查询考勤时间错误，错误信息："+e.toString());
            return null;
        }
    }

    /**
     * 功能描述:
     * (学生查询定位精度)
     *
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/4/7 11:28
     */
    @Override
    public String getDbConfigAttendanceAccuracy() {
        try {
            return this.dbConfigMapper.selectValue("attendance_accuracy");
        } catch (Exception e) {
            logger.error("学生获取定位精度错误，错误信息："+e.toString());
            return null;
        }
    }

    /**
     * 功能描述:
     * (学生考勤)
     *
     * @param attendanceInfo 考勤信息实体类
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/4/7 15:57
     */
    @Override
    public String addAttendanceInfo(AttendanceInfo attendanceInfo) {
        try {
            String studentId = SecurityUtils.getSubject().getPrincipal().toString();
            Attendance attendance = this.attendanceMapper.selectAttendanceByAttendanceId(attendanceInfo.getId());
            if(attendance!=null){
                //对象不为空 有这个考勤
                if(attendance.getState().equals("1")){
                    //考勤状态为正常考勤
                    if(DateUtil.getDifferSeconds(attendance.getTimeStart())>=0&&DateUtil.getDifferSeconds(attendance.getTimeEnd())<=0){
                        //当前时间大于等于开始时间且小于等于结束时间
                        if(this.attendanceInfoMapper.selectAttendanceInfoMac(attendanceInfo.getId(), attendanceInfo.getMac())==0){
                            //此mac在当前假条没有考勤过
                            if(this.attendanceInfoMapper.selectAttendanceInfoTime(attendanceInfo.getId(), studentId) == null){
                                //考勤时间为空 即为没有考勤过
                                attendanceInfo.setStudent(studentId);
                                attendanceInfo.setType("1");
                                if(this.attendanceInfoMapper.insertAttendanceInfoStudent(attendanceInfo)==1){
                                    return "success";
                                }
                            }

                        }
                    }
                }
            }
            return "error";
        } catch (Exception e) {
            logger.error("学生考勤错误，错误信息："+e.toString());
            return "error";
        }
    }

    /**
     * 功能描述:
     * (学生取消请假申请)
     *
     * @param leaveId 假条ID
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/4/24 15:00
     */
    @Override
    public String changeCancelLeave(Long leaveId) {
        try {
            String studentId = SecurityUtils.getSubject().getPrincipal().toString();
            Student student = this.studentMapper.selectStudent(studentId);
            if(this.leaveMapper.selectLeaveByStudentIdNum(leaveId, studentId) == 1){
                if(this.leaveMapper.updateLeaveState(leaveId, null, "10", null)==1){
                    //学生提醒消息
                    String yblaasTitle = this.dbConfigMapper.selectValue("yblaas_title");
                    messageUtil.Qq(student.getStudentQq(), yblaasTitle,5, leaveId.toString());
                    messageUtil.Email(student.getStudentEmail(), yblaasTitle, 5, leaveId.toString());
                    return "success";
                }
            }
            return "error";
        } catch (Exception e) {
            logger.error("学生取消假条申请错误，错误信息："+e.toString());
            return "error";
        }
    }

    /**
     * 功能描述:
     * (取消邮箱绑定)
     *
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/5/15 16:46
     */
    @Override
    public String cancelStudentEmail() {
        try {
            String studentId = (String) SecurityUtils.getSubject().getPrincipal();
            if(this.studentMapper.updateStudentEmail(studentId, null)==1){
                return "success";
            }else{
                return "error";
            }
        } catch (Exception e) {
            logger.error("学生取消绑定邮箱错误，错误信息：",e);
            return "error";
        }
    }

    /**
     * 功能描述:
     * (计算请假天数)
     *
     * @param date1 开始时间
     * @param date2 结束时间
     * @return : int
     * @author : xiaozhu
     * @date : 2020/3/22 23:11
     */
    private int differentDays(Date date1, Date date2){

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1= cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if(year1 != year2)   //同一年
        {
            int timeDistance = 0 ;
            for(int i = year1 ; i < year2 ; i ++)
            {
                if(i%4==0 && i%100!=0 || i%400==0)    //闰年
                {
                    timeDistance += 366;
                }
                else    //不是闰年
                {
                    timeDistance += 365;
                }
            }
            return timeDistance + (day2-day1) ;
        }
        else    //不同年
        {
            return day2-day1;
        }
    }

    private Email getEmail(){
        try {
            Email email = new Email();
            email.setEmail(dbConfigMapper.selectValue("email"));
            email.setEmailCall(dbConfigMapper.selectValue("email_call"));
            email.setEmailHost(dbConfigMapper.selectValue("email_host"));
            email.setEmailName(dbConfigMapper.selectValue("email_name"));
            email.setEmailPassword(dbConfigMapper.selectValue("email_password"));
            email.setEmailPort(dbConfigMapper.selectValue("email_port"));
            return email;
        } catch (Exception e) {
            logger.error("查询系统的右键配置信息错误，错误信息：",e);
            return null;
        }
    }
}
