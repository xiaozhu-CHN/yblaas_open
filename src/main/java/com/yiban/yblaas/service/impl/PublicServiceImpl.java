package com.yiban.yblaas.service.impl;

import cn.yiban.open.FrameUtil;
import cn.yiban.open.common.User;
import cn.yiban.util.HTTPSimple;
import com.yiban.yblaas.domain.*;
import com.yiban.yblaas.mapper.*;
import com.yiban.yblaas.service.PublicService;
import com.yiban.yblaas.shiro.VirtualType;
import com.yiban.yblaas.shiro.authc.UserToken;
import com.yiban.yblaas.thread.EmailThread;
import com.yiban.yblaas.thread.MessageThread;
import com.yiban.yblaas.thread.QqThread;
import com.yiban.yblaas.util.DateUtil;
import com.yiban.yblaas.util.MessageUtil;
import com.yiban.yblaas.util.YbApiUtil;
import net.sf.json.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.unit.DataUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @program: yblaas
 * @description: publicService接口的实现类
 * @author: xiaozhu
 * @create: 2020-03-17 23:17
 **/
@Service
@Transactional
public class PublicServiceImpl implements PublicService {

    private static final Logger logger = LoggerFactory.getLogger(PublicServiceImpl.class);

    @Autowired
    private DbConfigMapper dbConfigMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private EclassMapper eclassMapper;
    @Autowired
    private RolesMapper rolesMapper;
    @Autowired
    private CollegeMapper collegeMapper;
    @Autowired
    private VerificationMapper verificationMapper;
    @Autowired
    private MessageUtil messageUtil;

    @Override
    public String userLogin(HttpServletRequest request, HttpServletResponse response) {
        try {
            String appid = dbConfigMapper.selectValue("yiban_appId");
            String secrt = dbConfigMapper.selectValue("yiban_appsecret");
            String cburl = dbConfigMapper.selectValue("yiban_url");
            FrameUtil util = new FrameUtil(request, response, appid, secrt, cburl);
            if(util.perform()){
                String yibanThis = dbConfigMapper.selectValue("yiban_this");
                String token = util.getAccessToken();
                User user = new User(token);
                JSONObject jsonObject = JSONObject.fromObject(user.me());
                JSONObject json = jsonObject.getJSONObject("info");
                String yibanUserId = json.getString("yb_userid");
                if(!jsonObject.getString("status").equals("success")){
                    //接口错误返回500错误
                    return "500";
                }
                if(!(yibanThis == null || yibanThis.equals(""))){
                    //开启了拦截非本校
                    if(!json.getString("yb_schoolid").equals(yibanThis)){
                        //返回403
                        return "403";
                    }
                }
                String usertype = "500";
                if(this.teacherMapper.selectByTeacherId(yibanUserId)==1){
                    //老师表查询到信息
                    usertype = "teacher";
                }

                if(this.studentMapper.selectByStudentId(yibanUserId)==1){
                    usertype = "student";
                }
                if(!usertype.equals("500")){
                    //已经存在的表可以查询到信息直接登陆即可
                    Subject subject = SecurityUtils.getSubject();
                    UserToken shiroToken = new UserToken(yibanUserId, token, VirtualType.YIBAN);
                    try {
                        subject.login(shiroToken);
                        try {
                            return usertype;
                        }catch (UnauthorizedException exception){
                            logger.error("易班用户"+yibanUserId+"Shiro角色授权或者权限授权失败。错误信息："+exception.toString());
                            return "500";
                        }
                    }catch (AuthenticationException e){
                        logger.info("易班用户"+yibanUserId+"Shiro认证失败。错误信息："+e.toString());
                        return "500";
                    }
                }else{
                    //需要注册
                    String yibanSchool = dbConfigMapper.selectValue("yiban_school");
                    //用户需要注册
                    if(yibanSchool.equals("true")){
                        //具备校级权限
                        JSONObject jsonObjectSchool = JSONObject.fromObject(user.realme());
                        JSONObject jsonSchool = jsonObjectSchool.getJSONObject("info");
                        if(jsonSchool.getString("yb_identity").equals("学生")){
                            return this.studentRegister(token);
                        }else if(jsonSchool.getString("yb_identity").equals("老师") || jsonSchool.getString("yb_identity").equals("辅导员")){
                            return this.teacherRegister(token);
                        }else {
                            return "403";
                        }
                    }else{
                        //没有校级权限 需要用户选择
                        Session session = SecurityUtils.getSubject().getSession();
                        session.setAttribute("token",token);
                        return "register";
                    }
                }
            }
            return null;
        } catch (Exception e) {
            logger.info("易班用户登陆。");
            //返回重定向到站内地址的标记
            return "error";
        }
    }

    /**
     * 功能描述:
     * (有校级权限的学生注册的方法)
     *
     * @param token 易班用户的token
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/3/18 16:08
     */
    private String studentRegister(String token){
        try {
            String verifyUrl = YbApiUtil.VERIFY_ME+"?access_token="+token;
            JSONObject jsonObject = JSONObject.fromObject(HTTPSimple.GET(verifyUrl)).getJSONObject("info");
            Student student =new Student();
            student.setStudent(jsonObject.getString("yb_userid"));
            student.setNumberId(jsonObject.getString("yb_studentid"));
            student.setName(jsonObject.getString("yb_realname"));
            student.setEclassId(this.eclassMapper.selectEclassId(jsonObject.getString("yb_classname")));
            //向学生表插入信息
            this.studentMapper.insertStudent(student);
            //向shiro表插入角色信息
            this.rolesMapper.insertRoles(jsonObject.getString("yb_userid"),"student");

            //登陆和获取权限
            Subject subject = SecurityUtils.getSubject();
            UserToken shiroToken = new UserToken(jsonObject.getString("yb_userid"), token, VirtualType.YIBAN);
            try {
                subject.login(shiroToken);
                try {
                    return "student";
                }catch (UnauthorizedException exception){
                    logger.error("易班学生用户"+jsonObject.getString("yb_userid")+"Shiro角色授权或者权限授权失败。错误信息："+exception.toString());
                    return "500";
                }
            }catch (AuthenticationException e){
                logger.info("易班学生"+jsonObject.getString("yb_userid")+"Shiro认证失败。错误信息："+e.toString());
                return "500";
            }
        } catch (Exception e) {
            logger.error("具有校级权限的学生注册失败，错误信息："+e.toString());
            return "500";
        }
    }

    /**
     * 功能描述:
     * (有校级权限老师注册方法)
     *
     * @param token 易班用户token
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/3/18 16:08
     */
    private String teacherRegister(String token){
        try {
            String verifyUrl = YbApiUtil.VERIFY_ME+"?access_token="+token;
            JSONObject jsonObject = JSONObject.fromObject(HTTPSimple.GET(verifyUrl)).getJSONObject("info");
            Teacher teacher = new Teacher();
            teacher.setTeacher(jsonObject.getString("yb_userid"));
            teacher.setName(jsonObject.getString("yb_realname"));
            teacher.setCollegeId(this.collegeMapper.selectCollegeId(jsonObject.getString("yb_collegename")));
            //向老师表插入信息
            this.teacherMapper.insertTeacher(teacher);
            //向shiro表插入角色信息
            this.rolesMapper.insertRoles(jsonObject.getString("yb_userid"),"teacher");

            //登陆和获取权限
            Subject subject = SecurityUtils.getSubject();
            UserToken shiroToken = new UserToken(jsonObject.getString("yb_userid"), token, VirtualType.YIBAN);
            try {
                subject.login(shiroToken);
                try {
                    return "teacher";
                }catch (UnauthorizedException exception){
                    logger.error("易班老师用户"+jsonObject.getString("yb_userid")+"Shiro角色授权或者权限授权失败。错误信息："+exception.toString());
                    return "500";
                }
            }catch (AuthenticationException e){
                logger.info("易班老师"+jsonObject.getString("yb_userid")+"Shiro认证失败。错误信息："+e.toString());
                return "500";
            }
        } catch (Exception e) {
            logger.error("具有校级权限的老师注册失败，错误信息："+e.toString());
            return "500";
        }
    }

    /**
     * 功能描述:
     * (查询易班站内地址)
     *
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/3/18 13:02
     */
    @Override
    public String getYibanUrl() {
        return dbConfigMapper.selectValue("yiban_url");
    }

    /**
     * 功能描述:
     * (无校级权限的应用根据用户选择的角色进行注册)
     *
     * @param type 选择的类型 teacher-老师 student-学生
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/3/24 17:46
     */
    @Override
    public String userRegister(String type) {
        try {
            Subject subject = SecurityUtils.getSubject();
            Session session = subject.getSession();
            String token = (String) session.getAttribute("token");
            if(token == null || token.equals("")){
                return "error";
            }
            User user = new User(token);
            JSONObject jsonObject = JSONObject.fromObject(user.me());
            JSONObject json = jsonObject.getJSONObject("info");
            String yibanUserId = json.getString("yb_userid");
            String yibanUserName = json.getString("yb_username");
            if(type.equals("teacher")){
                Teacher teacher = new Teacher();
                teacher.setTeacher(yibanUserId);
                teacher.setName(yibanUserName);
                //向老师表插入信息
                this.teacherMapper.insertTeacher(teacher);
                //向shiro表插入角色信息
                this.rolesMapper.insertRoles(yibanUserId,"teacher");
            }else if(type.equals("student")){
                Student student = new Student();
                student.setStudent(yibanUserId);
                student.setName(yibanUserName);
                if(json.getString("yb_sex").equals("M")){
                    student.setSex("男");
                }else{
                    student.setSex("女");
                }
                //向学生表插入信息
                this.studentMapper.insertStudent(student);
                //向shiro表插入角色信息
                this.rolesMapper.insertRoles(yibanUserId,"student");
            }else{
                return "error";
            }

            //注册完成 进行登陆

            UserToken shiroToken = new UserToken(yibanUserId, token, VirtualType.YIBAN);
            try {
                subject.login(shiroToken);
                try {
                    session.removeAttribute("token");
                    return "success";
                }catch (UnauthorizedException exception){
                    logger.error("易班用户"+yibanUserId+"Shiro角色授权或者权限授权失败。错误信息："+exception.toString());
                    return "error";
                }
            }catch (AuthenticationException e){
                logger.info("易班用户"+yibanUserId+"Shiro认证失败。错误信息："+e.toString());
                return "error";
            }
        } catch (InvalidSessionException e) {
            logger.error("不具校级权限的易班用户注册失败，错误信息："+e.toString());
            return "error";
        }
    }

    /**
     * 功能描述:
     * (查询全部的学院信息)
     *
     * @return : java.util.List<com.yiban.yblaas.domain.College>
     * @author : xiaozhu
     * @date : 2020/3/24 17:48
     */
    @Override
    public List<College> getCollegeList() {
        try {
            return this.collegeMapper.selectCollegeAll();
        } catch (Exception e) {
            logger.error("查询学院信息错误，错误信息："+e.toString());
            return null;
        }
    }

    /**
     * 功能描述:
     * (根据学院查询班级接口)
     *
     * @param collegeId 学院Id
     * @return : java.util.List<com.yiban.yblaas.domain.Eclass>
     * @author : xiaozhu
     * @date : 2020/3/25 20:15
     */
    @Override
    public List<Eclass> getEclassList(Integer collegeId) {
        try {
            return this.eclassMapper.selectEclassByCollegeId(collegeId);
        } catch (Exception e) {
            logger.error("查询班级信息错误，错误信息："+e.toString());
            return null;
        }
    }

    /**
     * 功能描述:
     * (发送短信验证码接口)
     *
     * @param phone 手机号
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/3/25 22:24
     */
    @Override
    public String sendMessage(String phone) {
        try {
            String userId = (String) SecurityUtils.getSubject().getPrincipal();
            Verification verifications = this.verificationMapper.selectVerification(userId,"message");
            if(verifications!=null){
                if(verifications.getTime()!=null){
                    //小于60秒
                    if(DateUtil.getDifferSeconds(verifications.getTime())<60){
                        return "error";
                    }
                }
            }
            //减1000防止等于0
            String verification= String.valueOf(Math.round(Math.random()*(9999-1000)+1000));
            if(this.verificationMapper.insertVerification(userId, verification, "message", phone) ==1){
                //启动线程发送
                Thread messageThread = new MessageThread(phone, verification);
                messageThread.start();
                return "success";
            }
            return "error";
        } catch (Exception e) {
            logger.error("用户绑定手机发送消息错误，错误信息："+e.toString());
            return "error";
        }
    }

    /**
     * 功能描述:
     * (发送QQ验证码接口)
     *
     * @param userQq 用户QQ
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/3/28 20:29
     */
    @Override
    public String sendQq(String userQq) {
        try {
            String userId = (String) SecurityUtils.getSubject().getPrincipal();
            Verification verifications = this.verificationMapper.selectVerification(userId,"qq");
            if(verifications!=null){
                if(verifications.getTime()!=null){
                    //小于60秒
                    if(DateUtil.getDifferSeconds(verifications.getTime())<60){
                        return "error";
                    }
                }
            }
            String yblaasTitle = this.dbConfigMapper.selectValue("yblaas_title");
            //减1000防止等于0
            String verification= String.valueOf(Math.round(Math.random()*(9999-1000)+1000));
            if(this.verificationMapper.insertVerification(userId, verification, "qq", userQq) ==1){
                //启动线程发送
                Thread QqThread = new QqThread(userQq,yblaasTitle+"：您绑定QQ的验证码为【"+verification+"】");
                QqThread.start();
                return "success";
            }
            return "error";
        } catch (Exception e) {
            logger.error("用户绑定QQ发送消息错误，错误信息："+e.toString());
            return "error";
        }
    }

    /**
     * 功能描述:
     * (发送邮箱验证码接口)
     *
     * @param userEmail 用户邮箱
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/3/28 20:30
     */
    @Override
    public String sendEmail(String userEmail) {
        try {
            String userId = (String) SecurityUtils.getSubject().getPrincipal();
            Verification verifications = this.verificationMapper.selectVerification(userId,"email");
            if(verifications!=null){
                if(verifications.getTime()!=null){
                    //小于60秒
                    if(DateUtil.getDifferSeconds(verifications.getTime())<60){
                        return "error";
                    }
                }
            }
            String yblaasTitle = this.dbConfigMapper.selectValue("yblaas_title");
            //减1000防止等于0
            String verification= String.valueOf(Math.round(Math.random()*(9999-1000)+1000));
            if(this.verificationMapper.insertVerification(userId, verification, "email", userEmail) ==1){
                //启动发送
                messageUtil.Email(userEmail, yblaasTitle, 11, verification);
                return "success";
            }
            return "error";
        } catch (Exception e) {
            logger.error("用户绑定Email发送邮件错误，错误信息："+e.toString());
            return "error";
        }
    }

    /**
     * 功能描述:
     * (查询系统的标题、版权、备案号)
     *
     * @return : com.yiban.yblaas.domain.Yblaas
     * @author : xiaozhu
     * @date : 2020/4/14 21:20
     */
    @Override
    public Yblaas getYblaas() {
        try {
            Yblaas yblaas = new Yblaas();
            yblaas.setTitle(this.dbConfigMapper.selectValue("yblaas_title"));
            yblaas.setCopyright(this.dbConfigMapper.selectValue("yblaas_copyright"));
            yblaas.setBa(this.dbConfigMapper.selectValue("yblaas_ba"));
            return yblaas;
        } catch (Exception e) {
            logger.error("查询系统基本参数错误，错误信息："+e.toString());
            return null;
        }
    }

    /**
     * 功能描述:
     * (查询用户自定义高德API的key)
     *
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/9/11 0:56
     */
    public String getGaodeKey(){
        try {
            String gaodeKey = this.dbConfigMapper.selectValue("gaode_key");
            if(gaodeKey == null || gaodeKey.equals("")){
                return "c7aef2c066b99bd01c7c024d85671b9b";
            }else {
                return gaodeKey;
            }
        } catch (Exception e) {
            logger.error("查询系统高德API用户自定义key失败", e);
            return "c7aef2c066b99bd01c7c024d85671b9b";
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
