package com.yiban.yblaas.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yiban.yblaas.domain.*;
import com.yiban.yblaas.mapper.*;
import com.yiban.yblaas.service.TeacherService;
import com.yiban.yblaas.util.DateUtil;
import com.yiban.yblaas.util.MessageUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.NoTransactionException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

/**
 * @program: yblaas
 * @description: 老师接口的实现
 * @author: xiaozhu
 * @create: 2020-03-31 09:28
 **/
@Service
@Transactional
public class TeacherServiceImpl implements TeacherService {

    private static final Logger logger = LoggerFactory.getLogger(TeacherServiceImpl.class);

    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private VerificationMapper verificationMapper;
    @Autowired
    private LeaveMapper leaveMapper;
    @Autowired
    private DbConfigMapper dbConfigMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private AttendanceMapper attendanceMapper;
    @Autowired
    private AttendanceInfoMapper attendanceInfoMapper;
    @Autowired
    private EclassMapper eclassMapper;
    @Autowired
    private RolesMapper rolesMapper;
    @Autowired
    private CollegeMapper collegeMapper;
    @Autowired
    private MessageUtil messageUtil;

    /**
     * 功能描述:
     * (查询老师姓名)
     *
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/3/31 9:31
     */
    @Override
    public String getTeacherName() {
        try {
            String teacherId = (String) SecurityUtils.getSubject().getPrincipal();
            return this.teacherMapper.selectTeacherName(teacherId);
        } catch (Exception e) {
            logger.error("查询老师姓名错误，错误信息："+e.toString());
            return null;
        }
    }

    /**
     * 功能描述:
     * (查询老师角色)
     *
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/3/31 10:33
     */
    @Override
    public String getTeacherRoles() {
        try {
            Subject subject = SecurityUtils.getSubject();
            if(subject.hasRole("teacher")){
                return "teacher";
            }else if(subject.hasRole("fdy")){
                return "fdy";
            }else if(subject.hasRole("xyld")){
                return "xyld";
            }else if(subject.hasRole("xgc")){
                return "xgc";
            }else {
                return null;
            }
        } catch (Exception e) {
            logger.error("查询老师角色错误，错误信息："+e.toString());
            return null;
        }
    }

    /**
     * 功能描述:
     * (查询老师信息)
     *
     * @return : com.yiban.yblaas.domain.Teacher
     * @author : xiaozhu
     * @date : 2020/3/31 13:46
     */
    @Override
    public Teacher getTeacherData() {
        try {
            String teacherId = (String) SecurityUtils.getSubject().getPrincipal();
            return this.teacherMapper.selectTeacher(teacherId);
        } catch (Exception e) {
            logger.error("查询老师信息失败，错误信息："+e.toString());
            return null;
        }
    }

    /**
     * 功能描述:
     * (老师绑定QQ)
     *
     * @param teacherQq 老师QQ
     * @param verification 验证码
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/3/31 22:58
     */
    @Override
    public String changeTeacherQq(String teacherQq, String verification) {
        try {
            String teacherId = (String) SecurityUtils.getSubject().getPrincipal();
            Verification verificationDomain = this.verificationMapper.selectVerification(teacherId,"qq");
            if(verificationDomain!=null){
                //验证码已经过期或者验证字段不对 半个钟有效期
                if(DateUtil.getDifferSeconds(verificationDomain.getTime())>1800){
                    return "error";
                }
            }
            if(verification.equals(verificationDomain.getVerification())&&teacherQq.equals(verificationDomain.getField())){
                if(this.teacherMapper.updateTeacherQq(teacherId, teacherQq)==1){
                    return "success";
                }else{
                    return "error";
                }
            }else{
                return "error";
            }
        } catch (Exception e) {
            logger.error("老师绑定QQ错误，错误信息："+e.toString());
            return "error";
        }
    }

    /**
     * 功能描述:
     * (老师绑定邮箱)
     *
     * @param teacherEmail 老师邮箱
     * @param verification 验证码
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/3/31 23:42
     */
    @Override
    public String changeTeacherEmail(String teacherEmail, String verification) {
        try {
            String teacherId = (String) SecurityUtils.getSubject().getPrincipal();
            Verification verificationDomain = this.verificationMapper.selectVerification(teacherId,"email");
            if(verificationDomain!=null){
                //验证码已经过期或者验证字段不对 半个钟有效期
                if(DateUtil.getDifferSeconds(verificationDomain.getTime())>1800){
                    return "error";
                }
            }
            if(verification.equals(verificationDomain.getVerification())&&teacherEmail.equals(verificationDomain.getField())){
                if(this.teacherMapper.updateTeacherEmail(teacherId, teacherEmail)==1){
                    return "success";
                }else{
                    return "error";
                }
            }else{
                return "error";
            }
        } catch (Exception e) {
            logger.error("老师绑定邮箱错误，错误信息："+e.toString());
            return "error";
        }
    }

    /**
     * 功能描述:
     * (老师更新个人信息)
     *
     * @param teacher 老师实体类
     * @param verification 手机验证码
     * @return : java.lang.String  error1=验证码错误 error2-个人信息未发生变化
     * @author : xiaozhu
     * @date : 2020/4/1 0:05
     */
    @Override
    public String changeTeacher(Teacher teacher, String verification) {
        try {
            String teacherId = (String) SecurityUtils.getSubject().getPrincipal();
//            Verification verificationDomain = this.verificationMapper.selectVerification(teacherId,"message");
//            if(verificationDomain!=null){
//                //验证码已经过期或者验证字段不对
//                if(DateUtil.getDifferSeconds(verificationDomain.getTime())>600){
//                    return "error1";
//                }
//            }
//            if(verification.equals(verificationDomain.getVerification())&&teacher.getTeacherTell().equals(verificationDomain.getField())){
                teacher.setTeacher(teacherId);
                if(this.teacherMapper.updateTeacher(teacher)==1){
                    return "success";
                }else{
                    return "error2";
                }
//            }else{
//                return "error1";
//            }
        } catch (Exception e) {
            logger.error("老师更新个人信息错误，错误信息："+e.toString());
            return "error1";
        }
    }

    /**
     * 功能描述:
     * (老师查询学生最后一次假条记录)
     *
     * @param numberId 学生学号
     * @return : com.yiban.yblaas.domain.LeaveTeacherQuery
     * @author : xiaozhu
     * @date : 2020/4/1 9:21
     */
    @Override
    public LeaveTeacherQuery getLeaveByNumberId(String numberId) {
        try {
            return this.leaveMapper.selectLeaveTeacherQueryByNumberId(numberId);
        } catch (Exception e) {
            logger.error("老师查询学生最后一次假条错误，错误信息："+e.toString());
            return null;
        }
    }

    /**
     * 功能描述:
     * (查询假条的审核流程)
     *
     * @param leaveId 假条ID
     * @return : com.yiban.yblaas.domain.LeaveTeacherStep
     * @author : xiaozhu
     * @date : 2020/4/1 19:16
     */
    @Override
    public LeaveTeacherStep getLeaveStepById(Long leaveId) {
        try {
            return this.leaveMapper.selectLeaveTeacherStepById(leaveId);
        } catch (Exception e) {
            logger.error("查询假条审核流程错误，错误信息："+e.toString());
            return null;
        }
    }

    /**
     * 功能描述:
     * (老师查询待审核假条)
     *
     * @return : java.util.List<com.yiban.yblaas.domain.LeaveTeacherQuery>
     * @author : xiaozhu
     * @date : 2020/4/1 22:49
     */
    @Override
    public List<LeaveTeacherQuery> getLeaveByDsh() {
        try {
            Subject subject = SecurityUtils.getSubject();
            if(subject.hasRole("fdy")){
                String teacherId = (String) subject.getPrincipal();
                return this.leaveMapper.selectLeaveTeacherQueryListByDsh(teacherId, null, "fdy");
            }
            if(subject.hasRole("xyld")){
                String teacherId = (String) subject.getPrincipal();
                Teacher teacher = this.teacherMapper.selectTeacher(teacherId);
                if(teacher.getCollegeId() == null || teacher.getCollegeId() == 0){
                    return null;
                }else {
                    return this.leaveMapper.selectLeaveTeacherQueryListByDsh(null, teacher.getCollegeId(), "xyld");
                }
            }
            if(subject.hasRole("xgc")){
                return this.leaveMapper.selectLeaveTeacherQueryListByDsh(null, null, "xgc");
            }
            return null;
        } catch (Exception e) {
            logger.error("老师查询待审核假条错误，错误信息："+e.toString());
            return null;
        }
    }

    /**
     * 功能描述:
     * (老师审核学生假条)
     *
     * @param leaveId 假条Id
     * @param result 审核结果
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/4/2 21:30
     */
    @Override
    public String changeLeaveStateDsh(Long leaveId, Boolean result) {
        try {
            LeaveTeacherQuery leaveTeacherQuery = this.leaveMapper.selectLeaveTeacherQueryById(leaveId);
            Subject subject = SecurityUtils.getSubject();
            String teacherId = (String) subject.getPrincipal();
            String state = leaveTeacherQuery.getState();
            Integer day = leaveTeacherQuery.getDay();
            Integer updateNumber = 0;

            Integer leaveState = 0;

            if(state == null|| state.equals("")){
                return "error";
            }else if(state.equals("0")){
                //待辅导员审核
                if(subject.hasRole("fdy")){
                    if(result){
                        Integer dayXyld = Integer.parseInt(this.dbConfigMapper.selectValue("leave_xyld"));
                        if(day>dayXyld){
                            //请假天数大于设定的值 进入二级审批
                            updateNumber = this.leaveMapper.updateLeaveState(leaveId, teacherId, "2", "fdy");
                            leaveState = 2;
                        }else {
                            updateNumber = this.leaveMapper.updateLeaveState(leaveId, teacherId, "6", "fdy");
                            leaveState = 6;
                        }
                    }else{
                        updateNumber = this.leaveMapper.updateLeaveState(leaveId, teacherId, "1", "fdy");
                        leaveState = 1;
                    }

                }
            }else if(state.equals("2")){
                //待学院审核
                if(subject.hasRole("xyld")){
                    if(result){
                        Integer dayXgc = Integer.parseInt(this.dbConfigMapper.selectValue("leave_xgc"));
                        if(day>dayXgc){
                            //请假天数大于设定的值 进入三级审批
                            updateNumber = this.leaveMapper.updateLeaveState(leaveId, teacherId, "4", "xyld");
                            leaveState = 4;
                        }else {
                            updateNumber = this.leaveMapper.updateLeaveState(leaveId, teacherId, "6", "xyld");
                            leaveState = 6;
                        }
                    }else {
                        updateNumber = this.leaveMapper.updateLeaveState(leaveId, teacherId, "3", "xyld");
                        leaveState = 3;
                    }
                }
            }else if(state.equals("4")){
                //待学工处审核
                if(subject.hasRole("xgc")){
                    if(result){
                        updateNumber = this.leaveMapper.updateLeaveState(leaveId, teacherId, "6", "xgc");
                        leaveState = 6;
                    }else {
                        updateNumber = this.leaveMapper.updateLeaveState(leaveId, teacherId, "5", "xgc");
                        leaveState = 5;
                    }
                }
            }
            if(updateNumber == 1){
                sendMessageLeave(leaveId, leaveState);
                return "success";
            }else {
                return "error";
            }
        } catch (NumberFormatException e) {
            logger.error("老师审核假条错误，错误信息："+e.toString());
            return "error";
        }
    }

    /**
     * 功能描述:
     * (假条审核消息提醒)
     *
     * @param leaveId 假条ID
     * @param leaveState 假条状态
     * @return : void
     * @author : xiaozhu
     * @date : 2020/4/25 18:11
     */
    private void sendMessageLeave(Long leaveId, Integer leaveState){
        try {
            Student student = this.studentMapper.selectStudent(this.leaveMapper.selectLeaveStudentIdById(leaveId));
            String yblaasTitle = this.dbConfigMapper.selectValue("yblaas_title");
            if(leaveState == 6){
                messageUtil.Qq(student.getStudentQq(), yblaasTitle,2, leaveId.toString());
                messageUtil.Email(student.getStudentEmail(), yblaasTitle, 2, leaveId.toString());
            }else if(leaveState == 1 || leaveState == 3 || leaveId == 5){
                messageUtil.Qq(student.getStudentQq(), yblaasTitle,3, leaveId.toString());
                messageUtil.Email(student.getStudentEmail(), yblaasTitle, 3, leaveId.toString());
            }else if(leaveState == 2){
                //学院审核
                Eclass eclass = this.eclassMapper.selectEclass(student.getEclassId());
                List<Teacher> teachers = this.teacherMapper.selectTeacherByCollegeIdRoleXyld(eclass.getCollegeId());
                for (Teacher teacher : teachers) {
                    messageUtil.Qq(teacher.getTeacherQq(), yblaasTitle,7, leaveId.toString());
                    messageUtil.Email(teacher.getTeacherEmail(), yblaasTitle, 7, leaveId.toString());
                }
            }else if(leaveState == 4){
                List<Teacher> teachers = this.teacherMapper.selectTeacherByRoleXgc();
                for (Teacher teacher : teachers) {
                    messageUtil.Qq(teacher.getTeacherQq(), yblaasTitle,7, leaveId.toString());
                    messageUtil.Email(teacher.getTeacherEmail(), yblaasTitle, 7, leaveId.toString());
                }
            }else if(leaveState == 7){
                messageUtil.Qq(student.getStudentQq(), yblaasTitle,4, leaveId.toString());
                messageUtil.Email(student.getStudentEmail(), yblaasTitle, 4, leaveId.toString());
            }
        } catch (Exception e) {
            logger.error("假条审核消息提醒发送错误，错误信息："+e.toString());
        }
    }

    /**
     * 功能描述:
     * (老师查询待销假假条)
     *
     * @return : java.util.List<com.yiban.yblaas.domain.LeaveTeacherQuery>
     * @author : xiaozhu
     * @date : 2020/4/3 11:34
     */
    @Override
    public List<LeaveTeacherQuery> getLeaveByDxj() {
        try {
            Subject subject = SecurityUtils.getSubject();
            if(subject.hasRole("fdy")){
                String teacherId = (String) subject.getPrincipal();
                Integer dayXyld = Integer.parseInt(this.dbConfigMapper.selectValue("leave_xyld"));
                return this.leaveMapper.selectLeaveTeacherQueryListByDxj(teacherId,null,"fdy",dayXyld,null);
            }
            if(subject.hasRole("xyld")){
                String teacherId = (String) subject.getPrincipal();
                Teacher teacher = this.teacherMapper.selectTeacher(teacherId);
                if(teacher.getCollegeId() == null || teacher.getCollegeId() == 0){
                    return null;
                }else {
                    Integer dayXyld = Integer.parseInt(this.dbConfigMapper.selectValue("leave_xyld"));
                    Integer dayXgc = Integer.parseInt(this.dbConfigMapper.selectValue("leave_xgc"));
                    return this.leaveMapper.selectLeaveTeacherQueryListByDxj(null, teacher.getCollegeId(), "xyld", dayXyld, dayXgc);
                }
            }
            if(subject.hasRole("xgc")){
                Integer dayXgc = Integer.parseInt(this.dbConfigMapper.selectValue("leave_xgc"));
                return this.leaveMapper.selectLeaveTeacherQueryListByDxj(null, null, "xgc", null, dayXgc);
            }
            return null;
        } catch (Exception e) {
            logger.error("老师查询待销假假条错误，错误信息："+e.toString());
            return null;
        }
    }

    /**
     * 功能描述:
     * (老师对假条进行销假)
     *
     * @param leaveId 假条Id
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/4/3 12:07
     */
    @Override
    public String changeLeaveStateDxj(Long leaveId) {
        try {
            LeaveTeacherQuery leaveTeacherQuery = this.leaveMapper.selectLeaveTeacherQueryById(leaveId);
            Subject subject = SecurityUtils.getSubject();
            String teacherId = (String) subject.getPrincipal();
            String state = leaveTeacherQuery.getState();
            Integer day = leaveTeacherQuery.getDay();
            Integer updateNumber = 0;
            if(state == null|| state.equals("")){
                return "error";
            }else if(state.equals("6")){
                if(subject.hasRole("fdy")){
                    //待辅导员审核
                    Integer dayXyld = Integer.parseInt(this.dbConfigMapper.selectValue("leave_xyld"));
                    if(day<=dayXyld){
                        //请假天数小于等于设定的值 辅导员可以进行销假
                        updateNumber = this.leaveMapper.updateLeaveStateXj(leaveId, teacherId);
                    }
                }
                if(subject.hasRole("xyld")){
                    //学院审核
                    Integer dayXyld = Integer.parseInt(this.dbConfigMapper.selectValue("leave_xyld"));
                    Integer dayXgc = Integer.parseInt(this.dbConfigMapper.selectValue("leave_xgc"));
                    if(day<=dayXgc&&day>dayXyld){
                        //请假天数小于等于设定的值 学院领导可以进行销假
                        updateNumber = this.leaveMapper.updateLeaveStateXj(leaveId, teacherId);
                    }
                }
                if(subject.hasRole("xgc")){
                    Integer dayXgc = Integer.parseInt(this.dbConfigMapper.selectValue("leave_xgc"));
                    if(day>dayXgc){
                        updateNumber = this.leaveMapper.updateLeaveStateXj(leaveId, teacherId);
                    }
                }
            }
            if(updateNumber == 1){
                sendMessageLeave(leaveId, 7);
                return "success";
            }else {
                return "error";
            }
        } catch (NumberFormatException e) {
            logger.error("老师销假假条错误，错误信息："+e.toString());
            return "error";
        }
    }

    /**
     * 功能描述:
     * (老师查询学生信息)
     *
     * @param leaveId 假条Id
     * @return : com.yiban.yblaas.domain.LeaveTeacherStudent
     * @author : xiaozhu
     * @date : 2020/4/3 13:12
     */
    @Override
    public LeaveTeacherStudent getStudentData(Long leaveId) {
        try {
            return this.studentMapper.selectLeaveTeacherStudentByLeaveId(leaveId);
        } catch (Exception e) {
            logger.error("老师查询学生信息错误，错误信息："+e.toString());
            return null;
        }
    }

    /**
     * 功能描述:
     * (老师查询全部假条)
     *
     * @param draw datatables标记参数
     * @param length 每页长度
     * @param start 每页开始数
     * @param column 需排列序号
     * @param dir 正序倒序
     * @param type 搜索类型
     * @param seachString 搜索内容
     * @return : com.yiban.yblaas.domain.DataTables
     * @author : xiaozhu
     * @date : 2020/4/3 20:37
     */
    @Override
    public DataTables<LeaveTeacherQuery> getLeaveAll(Integer draw, Integer length, Integer start, Integer column, String dir, Integer type, String seachString) {
        try {
            Subject subject = SecurityUtils.getSubject();
            List<LeaveTeacherQuery> leaveTeacherQueries = null;
            Long recordsTotal = (long)0;
            if(subject.hasRole("fdy")){
                String teacherId = (String) subject.getPrincipal();
                if(type == 0){
                    PageHelper.startPage(start,length);
                    leaveTeacherQueries = this.leaveMapper.selectLeaveTeacherQueryListAll(column, dir, teacherId, null, "fdy", type, null, null);
                }else if(type == 1){
                    PageHelper.startPage(start,length);
                    leaveTeacherQueries = this.leaveMapper.selectLeaveTeacherQueryListAll(column, dir, teacherId, null, "fdy", type, seachString, null);
                }else if(type == 2){
                    PageHelper.startPage(start,length);
                    leaveTeacherQueries = this.leaveMapper.selectLeaveTeacherQueryListAll(column, dir, teacherId, null, "fdy", type, null, Long.parseLong(seachString));
                }else if(type == 3){
                    PageHelper.startPage(start,length);
                    leaveTeacherQueries = this.leaveMapper.selectLeaveTeacherQueryListAll(column, dir, teacherId, null, "fdy", type, seachString, null);
                }
                recordsTotal = this.leaveMapper.selectLeaveTeacherQueryListAllNum(teacherId, null, "fdy");
            }else if(subject.hasRole("xyld")){
                String teacherId = (String) subject.getPrincipal();
                Teacher teacher = this.teacherMapper.selectTeacher(teacherId);
                if(!(teacher.getCollegeId() == null || teacher.getCollegeId() == 0)){
                    if(type == 0){
                        PageHelper.startPage(start,length);
                        leaveTeacherQueries = this.leaveMapper.selectLeaveTeacherQueryListAll(column, dir, null, teacher.getCollegeId(), "xyld", type, null, null);
                    }else if(type == 1){
                        PageHelper.startPage(start,length);
                        leaveTeacherQueries = this.leaveMapper.selectLeaveTeacherQueryListAll(column, dir, null, teacher.getCollegeId(), "xyld", type, seachString, null);
                    }else if(type == 2){
                        PageHelper.startPage(start,length);
                        leaveTeacherQueries = this.leaveMapper.selectLeaveTeacherQueryListAll(column, dir, null, teacher.getCollegeId(), "xyld", type, null, Long.parseLong(seachString));
                    }else if(type == 3){
                        PageHelper.startPage(start,length);
                        leaveTeacherQueries = this.leaveMapper.selectLeaveTeacherQueryListAll(column, dir, null, teacher.getCollegeId(), "xyld", type, seachString, null);
                    }
                    recordsTotal = this.leaveMapper.selectLeaveTeacherQueryListAllNum(null, teacher.getCollegeId(), "xyld");
                }
            }else if(subject.hasRole("xgc")){
                if(type == 0){
                    PageHelper.startPage(start,length);
                    leaveTeacherQueries = this.leaveMapper.selectLeaveTeacherQueryListAll(column, dir, null, null, "xgc", type, null, null);
                }else if(type == 1){
                    PageHelper.startPage(start,length);
                    leaveTeacherQueries = this.leaveMapper.selectLeaveTeacherQueryListAll(column, dir, null, null, "xgc", type, seachString, null);
                }else if(type == 2){
                    PageHelper.startPage(start,length);
                    leaveTeacherQueries = this.leaveMapper.selectLeaveTeacherQueryListAll(column, dir, null, null, "xgc", type, null, Long.parseLong(seachString));
                }else if(type == 3){
                    PageHelper.startPage(start,length);
                    leaveTeacherQueries = this.leaveMapper.selectLeaveTeacherQueryListAll(column, dir, null, null, "xgc", type, seachString, null);
                }
                recordsTotal = this.leaveMapper.selectLeaveTeacherQueryListAllNum(null, null, "xgc");
            }
            if(leaveTeacherQueries == null){
                return new DataTables(draw, recordsTotal, (long)0, null, "查无记录");
            }else{
                PageInfo<LeaveTeacherQuery> pageInfo = new PageInfo<LeaveTeacherQuery>(leaveTeacherQueries);
                return new DataTables(draw, recordsTotal, (long) pageInfo.getTotal(), pageInfo.getList(), null);
            }
        } catch (Exception e) {
            logger.error("老师查询待销假假条错误，错误信息："+e.toString());
            return new DataTables(draw, null, null, null, "服务器错误");
        }
    }

    /**
     * 功能描述:
     * (老师查询定位精度)
     *
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/4/5 13:25
     */
    @Override
    public String getDbConfigAttendanceAccuracy() {
        try {
            return this.dbConfigMapper.selectValue("attendance_accuracy");
        } catch (Exception e) {
            logger.error("老师获取定位精度错误，错误信息："+e.toString());
            return null;
        }
    }

    /**
     * 功能描述:
     * (老师发起考勤)
     *
     * @param attendance 考勤实体类
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/4/5 14:55
     */
    @Override
    public String newAttendance(Attendance attendance) {
        try {
            String teacherId = (String) SecurityUtils.getSubject().getPrincipal();
            if(DateUtil.getDifferSecondsTwo(attendance.getTimeStart(), attendance.getTimeEnd())){
                attendance.setTeacher(teacherId);
                if(this.attendanceMapper.insertAttendance(attendance) == 1){
                    sendMeaasgeAttendance(teacherId, attendance.getEclassId(), attendance, 1);
                    return "success";
                }
            }
            return "error";
        } catch (Exception e) {
            logger.error("老师发起考勤错误，错误信息："+e.toString());
            return "error";
        }
    }

    /**
     * 功能描述:
     * (考勤消息提醒)
     *
     * @param teacherId 老师ID
     * @param eclassId 班级ID
     * @param attendance 考勤类
     * @param type 消息类型
     * @return : void
     * @author : xiaozhu
     * @date : 2020/4/25 20:49
     */
    private void sendMeaasgeAttendance(String teacherId, Integer eclassId, Attendance attendance, Integer type){
        try {
            Teacher teacher = this.teacherMapper.selectTeacher(teacherId);
            String yblaasTitle = this.dbConfigMapper.selectValue("yblaas_title");
            List<Student> students = this.studentMapper.selectStudentByEclassId(eclassId);
            if(type == 1){
                //新增考勤
                String content1 = "【"+attendance.getId().toString()+"】。\n"+"标题："+attendance.getName()+"\n开始时间："+DateUtil.getTimeString(attendance.getTimeStart())+"\n结束时间："+DateUtil.getTimeString(attendance.getTimeEnd())+"\n请记得按时参与考勤。";
                String content2 = "【"+attendance.getId().toString()+"】。<br/>"+"&nbsp; &nbsp; &nbsp; 标题："+attendance.getName()+"<br/>&nbsp; &nbsp; &nbsp; 开始时间："+DateUtil.getTimeString(attendance.getTimeStart())+"<br/>&nbsp; &nbsp; &nbsp; 结束时间："+DateUtil.getTimeString(attendance.getTimeEnd())+"<br/>&nbsp; &nbsp; &nbsp; 请记得按时参与考勤。";
                messageUtil.Qq(teacher.getTeacherQq(), yblaasTitle,8, attendance.getId().toString());
                messageUtil.Email(teacher.getTeacherEmail(), yblaasTitle, 8, attendance.getId().toString());
                for (Student student : students) {
                    messageUtil.Qq(student.getStudentQq(), yblaasTitle,6, content1);
                    messageUtil.Email(student.getStudentEmail(), yblaasTitle, 6, content2);
                }
            }else if(type == 2){
                //删除考勤
                messageUtil.Qq(teacher.getTeacherQq(), yblaasTitle,9, attendance.getId().toString());
                messageUtil.Email(teacher.getTeacherEmail(), yblaasTitle, 9, attendance.getId().toString());
                for (Student student : students) {
                    messageUtil.Qq(student.getStudentQq(), yblaasTitle,10, attendance.getId().toString());
                    messageUtil.Email(student.getStudentEmail(), yblaasTitle, 10, attendance.getId().toString());
                }
            }
        } catch (Exception e) {
            logger.error("考勤消息提醒错误，错误信息："+e.toString());
        }
    }
    /**
     * 功能描述:
     * (老师查询全部考勤)
     *
     * @param draw datatables标记
     * @param length 每页长度
     * @param start 开始数
     * @param column 排序的列
     * @param dir 正序反序
     * @param type 搜索类型
     * @param seachString 搜索字段
     * @return : com.yiban.yblaas.domain.DataTables
     * @author : xiaozhu
     * @date : 2020/4/5 20:17
     */
    @Override
    public DataTables<Attendance> getAttendanceAll(Integer draw, Integer length, Integer start, Integer column, String dir, Integer type, String seachString) {
        try {
            String teacherId = (String) SecurityUtils.getSubject().getPrincipal();
            List<Attendance> attendances = null;
            if(type == 0){
                PageHelper.startPage(start,length);
                attendances = this.attendanceMapper.selectAttendanceListByTeacher(teacherId, column, dir, type, null, null);
            }else if(type == 1){
                PageHelper.startPage(start,length);
                attendances = this.attendanceMapper.selectAttendanceListByTeacher(teacherId, column, dir, type, null, Long.parseLong(seachString));
            }else if(type == 2){
                PageHelper.startPage(start,length);
                attendances = this.attendanceMapper.selectAttendanceListByTeacher(teacherId, column, dir, type, seachString, null);
            }else if(type == 3){
                PageHelper.startPage(start,length);
                attendances = this.attendanceMapper.selectAttendanceListByTeacher(teacherId, column, dir, type, seachString, null);
            }
            Long recordsTotal = this.attendanceMapper.selectAttendanceListByTeacherNum(teacherId);
            if(attendances == null){
                return new DataTables(draw, recordsTotal, (long)0, null, "查无记录");
            }else{
                PageInfo<Attendance> pageInfo = new PageInfo<Attendance>(attendances);
                return new DataTables(draw, recordsTotal, (long) pageInfo.getTotal(), pageInfo.getList(), null);
            }
        } catch (NumberFormatException e) {
            logger.error("老师查询全部考勤错误，错误信息："+e.toString());
            return new DataTables(draw, null, null, null, "服务器错误");
        }
    }

    /**
     * 功能描述:
     * (老师查询考勤结果)
     *
     * @param attendanceId 考勤表ID
     * @return : java.util.List<com.yiban.yblaas.domain.TeacherAttendance>
     * @author : xiaozhu
     * @date : 2020/4/5 23:41
     */
    @Override
    public List<TeacherAttendance> getAttendanceInfoById(Long attendanceId) {
        try {
            return this.attendanceInfoMapper.selectTeacherAttendanceById(attendanceId);
        } catch (Exception e) {
            logger.error("老师查询考勤结果错误，错误信息"+e.toString());
            return null;
        }
    }

    /**
     * 功能描述:
     * (老师查询考勤结果的辅助信息)
     *
     * @param attendanceId 考勤Id
     * @return : com.yiban.yblaas.domain.AttendanceInfoData
     * @author : xiaozhu
     * @date : 2020/4/6 11:08
     */
    @Override
    public AttendanceInfoData getAttendanceInfoData(Long attendanceId) {
        try {
            Attendance attendance = this.attendanceMapper.selectAttendanceByAttendanceId(attendanceId);
            if(attendance != null){
                Integer eclassNumber = this.studentMapper.selectStudentNumByEclassId(attendance.getEclassId());
                Integer attendanceNumber = this.attendanceInfoMapper.selectAttendanceInfoNumByAttendanceId(attendanceId);
                AttendanceInfoData attendanceInfoData = new AttendanceInfoData();
                attendanceInfoData.setEclassNumber(eclassNumber);
                attendanceInfoData.setAttendanceNumber(attendanceNumber);
                attendanceInfoData.setAttendanceNumberNo(eclassNumber - attendanceNumber);
                attendanceInfoData.setLatitude(attendance.getLatitude());
                attendanceInfoData.setLongitude(attendance.getLongitude());
                attendanceInfoData.setAttendanceAccuracy(this.dbConfigMapper.selectValue("attendance_accuracy"));
                return attendanceInfoData;
            }else{
                return null;
            }
        } catch (Exception e) {
            logger.error("老师获取考勤结果的辅助信息错误，错误信息"+e.toString());
            return null;
        }
    }

    /**
     * 功能描述:
     * (老师手动学生考勤)
     *
     * @param attendanceId 考勤ID
     * @param studentId 学生ID
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/4/6 13:01
     */
    @Override
    public String addAttendanceInfo(Long attendanceId, String studentId) {
        try {
            String teacherId = (String) SecurityUtils.getSubject().getPrincipal();
            Attendance attendance = this.attendanceMapper.selectAttendanceByAttendanceId(attendanceId);
            if(attendance != null){
                if(teacherId.equals(attendance.getTeacher())){
                    if(this.attendanceInfoMapper.insertAttendanceInfoTeacher(attendanceId, studentId)==1){
                        return "success";
                    }
                }
            }
            return "error";
        } catch (Exception e) {
            logger.error("老师手动学生考勤错误，错误信息："+e.toString());
            return "error";
        }
    }

    /**
     * 功能描述:
     * (老师手动取消学生考勤)
     *
     * @param attendanceId 考勤ID
     * @param studentId 学生ID
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/4/6 13:10
     */
    @Override
    public String delAttendanceInfo(Long attendanceId, String studentId) {
        try {
            String teacherId = (String) SecurityUtils.getSubject().getPrincipal();
            Attendance attendance = this.attendanceMapper.selectAttendanceByAttendanceId(attendanceId);
            if(attendance != null){
                if(teacherId.equals(attendance.getTeacher())){
                    if(this.attendanceInfoMapper.deleteAttendanceInfoTeacher(attendanceId, studentId)==1){
                        return "success";
                    }
                }
            }
            return "error";
        } catch (Exception e) {
            logger.error("老师手动学生考勤错误，错误信息："+e.toString());
            return "error";
        }
    }

    /**
     * 功能描述:
     * (老师查询待考勤记录)
     *
     * @return : java.util.List<com.yiban.yblaas.domain.Attendance>
     * @author : xiaozhu
     * @date : 2020/4/6 14:07
     */
    @Override
    public List<Attendance> getAttendanceDkq() {
        try {
            String teacherId = (String) SecurityUtils.getSubject().getPrincipal();
            return this.attendanceMapper.selectAttendanceListDkqByTeacher(teacherId);
        } catch (Exception e) {
            logger.error("老师查询待考勤错误，错误信息："+e.toString());
            return null;
        }
    }

    /**
     * 功能描述:
     * (老师停止考勤)
     *
     * @param attendanceId 考勤ID
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/4/6 15:53
     */
    @Override
    public String changeAttendanceStop(Long attendanceId) {
        try {
            String teacherId = (String) SecurityUtils.getSubject().getPrincipal();
            Attendance attendance = this.attendanceMapper.selectAttendanceByAttendanceId(attendanceId);
            if(attendance != null){
                if(teacherId.equals(attendance.getTeacher())){
                    if(this.attendanceMapper.updateAttendanceStop(attendanceId, "2")==1){
                        return "success";
                    }
                }
            }
            return "error";
        } catch (Exception e) {
            logger.error("老师停止考勤错误，错误信息："+e.toString());
            return "error";
        }
    }

    /**
     * 功能描述:
     * (老师启用考勤)
     *
     * @param attendanceId 考勤ID
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/4/6 18:59
     */
    @Override
    public String changeAttendanceEnable(Long attendanceId) {
        try {
            String teacherId = (String) SecurityUtils.getSubject().getPrincipal();
            Attendance attendance = this.attendanceMapper.selectAttendanceByAttendanceId(attendanceId);
            if(attendance != null){
                if(teacherId.equals(attendance.getTeacher())){
                    if(this.attendanceMapper.updateAttendanceStop(attendanceId, "1")==1){
                        return "success";
                    }
                }
            }
            return "error";
        } catch (Exception e) {
            logger.error("老师启用考勤错误，错误信息："+e.toString());
            return "error";
        }
    }

    /**
     * 功能描述:
     * (老师删除考勤)
     *
     * @param attendanceId 考勤ID
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/4/6 19:07
     */
    @Override
    public String changeAttendanceDel(Long attendanceId) {
        try {
            String teacherId = (String) SecurityUtils.getSubject().getPrincipal();
            Attendance attendance = this.attendanceMapper.selectAttendanceByAttendanceId(attendanceId);
            if(attendance != null){
                if(teacherId.equals(attendance.getTeacher())){
                    if(this.attendanceMapper.updateAttendanceStop(attendanceId, "3")==1){
                        sendMeaasgeAttendance(teacherId, attendance.getEclassId(), attendance, 2);
                        return "success";
                    }
                }
            }
            return "error";
        } catch (Exception e) {
            logger.error("老师删除考勤错误，错误信息："+e.toString());
            return "error";
        }
    }

    /**
     * 功能描述:
     * (老师查询学生待审核信息)
     *
     * @return : java.util.List<com.yiban.yblaas.domain.Student>
     * @author : xiaozhu
     * @date : 2020/4/11 10:14
     */
    @Override
    public List<Student> getStudentListDsh() {
        try {
            String teacherId = (String) SecurityUtils.getSubject().getPrincipal();
            return this.studentMapper.selectStudentListDsh(teacherId);
        } catch (Exception e) {
            logger.error("老师查询学生待审核信息错误，错误信息："+e.toString());
            return null;
        }
    }

    /**
     * 功能描述:
     * (老师审核学生信息)
     *
     * @param studentId 学生ID
     * @param examine 审核结果
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/4/11 14:58
     */
    @Override
    public String changeStudentExamine(String studentId, String examine) {
        try {
            String teacherId = (String) SecurityUtils.getSubject().getPrincipal();
            String studentTeacherId = this.studentMapper.selectStudentTeacherId(studentId);
            if(studentTeacherId!=null && teacherId.equals(studentTeacherId)){
                if(this.studentMapper.updateStudentExamine(studentId, examine) == 1){
                    return "success";
                }
            }
            return "error";
        } catch (Exception e) {
            logger.error("老师审核学生信息错误，错误信息："+e.toString());
            return "error";
        }
    }

    /**
     * 功能描述:
     * (批量通过学生信息审核)
     *
     * @param studentIds 学生ID数组
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/4/11 15:59
     */
    @Override
    public String changeStudentExamines(String[] studentIds) {
        try {
            if(studentIds.length>0){
                String teacherId = (String) SecurityUtils.getSubject().getPrincipal();
                for (int i = 0; i < studentIds.length; i++) {
                    String studentTeacherId = this.studentMapper.selectStudentTeacherId(studentIds[i]);
                    if(studentTeacherId!=null && teacherId.equals(studentTeacherId)){
                        if(this.studentMapper.updateStudentExamine(studentIds[i], "2") != 1){
                            //手动回滚
                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                            return "error";
                        }
                    }else {
                        //手动回滚
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return "error";
                    }
                }
                return "success";
            }
            return "error";
        } catch (NoTransactionException e) {
            logger.error("批量审核通过学生信息错误，错误信息："+e.toString());
            return "error";
        }

    }

    /**
     * 功能描述:
     * (老师查询班级信息错误)
     *
     * @return : java.util.List<com.yiban.yblaas.domain.EclassTeacher>
     * @author : xiaozhu
     * @date : 2020/4/11 20:07
     */
    @Override
    public List<EclassTeacher> getEclassTeacherByTeacherId() {
        try {
            String teacherId = (String) SecurityUtils.getSubject().getPrincipal();
            return this.eclassMapper.selectEclassTeacherByTeacherId(teacherId);
        } catch (Exception e) {
            logger.error("老师查询班级信息错误，错误信息："+e.toString());
            return null;
        }
    }

    /**
     * 功能描述:
     * (查询班级学生信息错误)
     *
     * @return : java.util.List<com.yiban.yblaas.domain.TeacherStudent>
     * @author : xiaozhu
     * @date : 2020/4/11 23:04
     */
    @Override
    public List<TeacherStudent> getTeacherStudentList(Integer eclassId) {
        try {
            return this.studentMapper.selectTeacherStudentList(eclassId);
        } catch (Exception e) {
            logger.error("查询班级学生信息错误，错误信息："+e.toString());
            return null;
        }
    }

    /**
     * 功能描述:
     * (学院查询班级列表)
     *
     * @return : java.util.List<com.yiban.yblaas.domain.EclassTeacher>
     * @author : xiaozhu
     * @date : 2020/4/12 21:55
     */
    @Override
    public List<EclassTeacher> getEclassTeacherByCollegeId() {
        try {
            String teacherId = (String) SecurityUtils.getSubject().getPrincipal();
            Teacher teacher = this.teacherMapper.selectTeacher(teacherId);
            if(teacher.getCollegeId()!=null && !teacher.getCollegeId().equals("")){
                return this.eclassMapper.selectEclassTeacherByCollegeId(teacher.getCollegeId());
            }
            return null;
        } catch (Exception e) {
            logger.error("学院查询班级列表错误，错误信息"+e.toString());
            return null;
        }
    }

    /**
     * 功能描述:
     * (学院修改班级辅导员)
     *
     * @param teacherId 老师Id
     * @param eclassId 班级Id
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/4/13 10:16
     */
    @Override
    public String changeEclassTeacherId(String teacherId, Integer eclassId) {
        try {
            String teacherId2 = (String) SecurityUtils.getSubject().getPrincipal();
            Teacher teacher1 = this.teacherMapper.selectTeacher(teacherId);
            Teacher teacher2 = this.teacherMapper.selectTeacher(teacherId2);
            Eclass eclass = this.eclassMapper.selectEclass(eclassId);
            if(teacher1.getCollegeId()!=null && teacher2.getCollegeId()!=null && eclass.getCollegeId()!=null){
                if(teacher1.getCollegeId()==teacher2.getCollegeId() && teacher2.getCollegeId() == eclass.getCollegeId()){
                    this.delEclassTeacherId(eclassId);
                    if(this.eclassMapper.updateEclassTeacherId(teacherId, eclassId) == 1){
                        Roles roles = this.rolesMapper.selectRoles(teacherId);
                        if(!roles.getRoleName().equals("fdy")){
                            //不是辅导员权限的就给授权
                            if(this.rolesMapper.updateRoleName(roles.getId(),"fdy")==1){
                                return "success";
                            }else{
                                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                            }
                        }else{
                            return "success";
                        }
                    }
                }
            }
            return "error";
        } catch (Exception e) {
            logger.error("学院修改班级辅导员错误，错误信息:"+e.toString());
            return "error";
        }
    }

    /**
     * 功能描述:
     * (学院查询学院的老师)
     *
     * @return : java.util.List<com.yiban.yblaas.domain.Teacher>
     * @author : xiaozhu
     * @date : 2020/4/13 10:39
     */
    @Override
    public List<Teacher> getTeacherByCollegeId() {
        try {
            String teacherId = (String) SecurityUtils.getSubject().getPrincipal();
            Teacher teacher = this.teacherMapper.selectTeacher(teacherId);
            if(teacher.getCollegeId()!=null){
                return this.teacherMapper.selectTeacherByCollegeIdFdy(teacher.getCollegeId());
            }
            return null;
        } catch (Exception e) {
            logger.error("学院查询学院老师错误，错误信息："+e.toString());
            return null;
        }
    }

    /**
     * 功能描述:
     * (学院取消任命辅导员)
     *
     * @param eclassId 班级ID
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/4/13 12:14
     */
    @Override
    public String delEclassTeacherId(Integer eclassId) {
        try {
            String teacherId = (String) SecurityUtils.getSubject().getPrincipal();
            Teacher teacher = this.teacherMapper.selectTeacher(teacherId);
            Eclass eclass = this.eclassMapper.selectEclass(eclassId);
            if(eclass.getCollegeId()!=null&&teacher.getCollegeId()!=null&&eclass.getCollegeId().equals(teacher.getCollegeId())){
                if(eclass.getTeacher()!=null){
                    if(this.eclassMapper.updateEclassTeacherId(null,eclassId) == 1){
                        if(this.eclassMapper.selectEclassByTeacherIdNum(eclass.getTeacher())==0){
                            //需要去除fdy权限
                            Roles roles = this.rolesMapper.selectRoles(eclass.getTeacher());
                            if(this.rolesMapper.updateRoleName(roles.getId(),"teacher")==1){
                                return "success";
                            }else{
                                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                            }
                        }else{
                            return "success";
                        }
                    }

                }
            }
            return "error";
        } catch (Exception e) {
            logger.error("学院取消任命辅导员错误，错误信息："+e.toString());
            return "error";
        }
    }

    /**
     * 功能描述:
     * (学院新增班级)
     *
     * @param eclassName 班级名称
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/4/13 22:39
     */
    @Override
    public String addEclass(String eclassName) {
        try {
            String teacherId = (String) SecurityUtils.getSubject().getPrincipal();
            Teacher teacher = this.teacherMapper.selectTeacher(teacherId);
            if(teacher.getCollegeId() != null && teacher.getCollegeId() !=0){
                if(this.eclassMapper.insertEclass(teacher.getCollegeId(), eclassName) ==1){
                    return "success";
                }
            }
            return "error";
        } catch (Exception e) {
            logger.error("学院新增班级错误，错误信息："+e.toString());
            return "error";
        }
    }

    /**
     * 功能描述:
     * (学院修改班级名称)
     *
     * @param eclassName 班级名称
     * @param eclassId 班级ID
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/4/14 10:00
     */
    @Override
    public String changeEclassName(String eclassName, Integer eclassId) {
        try {
            String teacherId = (String) SecurityUtils.getSubject().getPrincipal();
            Teacher teacher = this.teacherMapper.selectTeacher(teacherId);
            Eclass eclass = this.eclassMapper.selectEclass(eclassId);
            if(eclass.getCollegeId()!=null&&teacher.getCollegeId()!=null&&eclass.getCollegeId().equals(teacher.getCollegeId())){
                if(this.eclassMapper.updateEclassName(eclassName, eclassId)==1){
                    return "success";
                }
            }
            return "error";
        } catch (Exception e) {
            logger.error("学院修改班级名称错误，错误信息："+e.toString());
            return "error";
        }
    }

    /**
     * 功能描述:
     * (学院删除班级)
     *
     * @param eclassId 班级ID
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/4/14 10:49
     */
    @Override
    public String delEclass(Integer eclassId) {
        try {
            String teacherId = (String) SecurityUtils.getSubject().getPrincipal();
            Teacher teacher = this.teacherMapper.selectTeacher(teacherId);
            Eclass eclass = this.eclassMapper.selectEclass(eclassId);
            if(eclass.getCollegeId()!=null&&teacher.getCollegeId()!=null&&eclass.getCollegeId().equals(teacher.getCollegeId())){
                //清空待审核和审核失败的学生的班级ID
                this.studentMapper.updateStudentEclassNull(eclassId);
                if(this.studentMapper.selectStudentNumByEclassId(eclassId)==0){
                    //班级人数为0 可删除
                    if(this.eclassMapper.deleteEclass(eclassId)==1){
                        return "success";
                    }
                }
            }
            return "error";
        } catch (Exception e) {
            logger.error("学院删除班级错误，错误信息："+e.toString());
            return "error";
        }
    }

    /**
     * 功能描述:
     * (学工处查询学院老师)
     *
     * @param collegeId 学院ID
     * @return : java.util.List<com.yiban.yblaas.domain.Teacher>
     * @author : xiaozhu
     * @date : 2020/4/14 14:57
     */
    @Override
    public List<Teacher> getTeacherXyld(Integer collegeId) {
        try {
            return this.teacherMapper.selectTeacherByCollegeIdXyld(collegeId);
        } catch (Exception e) {
            logger.error("学工处查询学院老师错误，错误信息："+e.toString());
            return null;
        }
    }

    /**
     * 功能描述:
     * (学工处任命/取消任命学院)
     *
     * @param teacherId 老师ID
     * @param role 任命角色
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/4/14 15:37
     */
    @Override
    public String changeTeacherXyldRole(String teacherId, String role) {
        try {
            Roles roles = this.rolesMapper.selectRoles(teacherId);
            Teacher teacher = this.teacherMapper.selectTeacher(teacherId);
            if(teacher.getCollegeId()!=null && teacher.getCollegeId()!=0){
                if(role.equals("xyld")){
                    if(roles.getRoleName().equals("teacher")){
                        if(this.rolesMapper.updateRoleName(roles.getId(), "xyld") == 1){
                            return "success";
                        }
                    }
                }
                if(role.equals("teacher")){
                    if(roles.getRoleName().equals("xyld")){
                        if(this.rolesMapper.updateRoleName(roles.getId(), "teacher") == 1){
                            return "success";
                        }
                    }
                }
            }

            return "error";
        } catch (Exception e) {
            logger.error("学工处任命/取消任命学院错误，错误信息："+e.toString());
            return "error";
        }
    }

    /**
     * 功能描述:
     * (学工处新增学院)
     *
     * @param collegeName 学院名称
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/4/14 16:23
     */
    @Override
    public String addCollege(String collegeName) {
        try {
            if(this.collegeMapper.insertCollege(collegeName)==1){
                return "success";
            }else {
                return "error";
            }
        } catch (Exception e) {
            logger.error("学工处新增学院错误，错误信息："+e.toString());
            return "error";
        }
    }

    /**
     * 功能描述:
     * (学工处修改学院名称)
     *
     * @param collegeName 学院名称
     * @param collegeId 学院ID
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/4/14 19:08
     */
    @Override
    public String changeCollege(String collegeName, Integer collegeId) {
        try {
            if(this.collegeMapper.updateCollege(collegeName, collegeId)==1){
                return "success";
            }else {
                return "error";
            }
        } catch (Exception e) {
            logger.error("学工处修改学院名称错误，错误信息："+e.toString());
            return "error";
        }
    }

    /**
     * 功能描述:
     * (学工处删除学院)
     *
     * @param collegeId 学院ID
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/4/14 21:58
     */
    @Override
    public String delCollege(Integer collegeId) {
        try {
            if(this.collegeMapper.deleteCollege(collegeId)==1){
                return "success";
            }else {
                return "error";
            }
        } catch (Exception e) {
            logger.error("学工处删除学院错误，错误信息："+e.toString());
            return "error";
        }
    }

    /**
     * 功能描述:
     * (老师查询待审核假条的数量)
     *
     * @return : java.lang.Integer
     * @author : xiaozhu
     * @date : 2020/4/23 22:01
     */
    @Override
    public Integer getLeaveByDxjNum() {
        try {
            Subject subject = SecurityUtils.getSubject();
            if(subject.hasRole("fdy")){
                String teacherId = subject.getPrincipal().toString();
                return this.leaveMapper.selectLeaveTeacherQueryListByDshNum(teacherId, null, "fdy");
            }
            if(subject.hasRole("xyld")){
                String teacherId = subject.getPrincipal().toString();
                Teacher teacher = this.teacherMapper.selectTeacher(teacherId);
                if(teacher.getCollegeId() == null || teacher.getCollegeId() == 0){
                    return null;
                }else {
                    return this.leaveMapper.selectLeaveTeacherQueryListByDshNum(null, teacher.getCollegeId(), "xyld");
                }
            }
            if(subject.hasRole("xgc")){
                return this.leaveMapper.selectLeaveTeacherQueryListByDshNum(null, null, "xgc");
            }
            return null;
        } catch (Exception e) {
            logger.error("老师查询待审核假条数量错误，错误信息："+e.toString());
            return null;
        }
    }

    /**
     * 功能描述:
     * (老师查询学生信息待审核数量)
     *
     * @return : java.lang.Integer
     * @author : xiaozhu
     * @date : 2020/4/23 22:50
     */
    @Override
    public Integer getStudentDshNum() {
        try {
            Subject subject = SecurityUtils.getSubject();
            if(subject.hasRole("fdy")){
                String teacherId = subject.getPrincipal().toString();
                return this.studentMapper.selectStudentListDshNum(teacherId);
            }
            return null;
        } catch (Exception e) {
            logger.error("老师查询学生信息待审核数量错误，错误信息："+e.toString());
            return null;
        }
    }

    /**
     * 功能描述:
     * (老师取消绑定邮箱)
     *
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/5/15 17:01
     */
    @Override
    public String cancelTeacherEmail() {
        try {
            String teacherId = SecurityUtils.getSubject().getPrincipal().toString();
            if(this.teacherMapper.updateTeacherEmail(teacherId, null)==1){
                return "success";
            }else{
                return "error";
            }
        } catch (Exception e) {
            logger.error("老师取消绑定邮箱错误，错误信息：",e);
            return "error";
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
