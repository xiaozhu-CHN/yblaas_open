package com.yiban.yblaas.controller;

import com.yiban.yblaas.service.impl.PublicServiceImpl;
import com.yiban.yblaas.shiro.VirtualType;
import com.yiban.yblaas.shiro.authc.UserToken;
import com.yiban.yblaas.util.RandomValidateCodeUtil;
import com.yiban.yblaas.util.RedisUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: yblaas
 * @description: 不设拦截器的Controller
 * @author: xiaozhu
 * @create: 2020-03-12 08:31
 **/
@Controller
@RequestMapping("/public")
public class PublicController {

    private static final Logger logger = LoggerFactory.getLogger(PublicController.class);

    @Autowired
    private PublicServiceImpl publicService;

    /**
     * 功能描述:
     * (管理员登陆页)
     *
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/3/14 20:38
     */
    @RequestMapping("admin_login")
    public String admin_login(ModelMap map){
        map.put("yblaas",this.publicService.getYblaas());
        return "public/admin_login";
    }

    /**
     * 功能描述:
     * (管理员登陆)
     *
     * @param adminUser 管理员用户名
     * @param adminPassword 管理员密码
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/3/14 20:38
     */
    @RequestMapping(value = "admin_login_ajax", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String admin_login_ajax(String adminUser,String adminPassword,String verification){
        Subject subject = SecurityUtils.getSubject();
        UserToken token = new UserToken(adminUser, adminPassword, VirtualType.ADMIN);
        RandomValidateCodeUtil randomValidateCodeUtil = new RandomValidateCodeUtil();
        try {
            if(randomValidateCodeUtil.isVerification(verification)){
                Session session = subject.getSession();
                subject.login(token);
                try {
                    session.setAttribute("adminUser",adminUser);
                    return "success";
                }catch (UnauthorizedException exception){
                    logger.error("超级管理员角色授权或者权限授权失败！");
                    return "error";
                }
            }else {
                return "error1";
            }
        }catch (AuthenticationException e){
            logger.info("超级管理员认证失败！");
            return "error";
        }
    }

    /**
     * 功能描述:
     * (易班接口过来的登陆处理)
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/3/21 14:04
     */
    @RequestMapping("user_login")
    public String user_login(HttpServletRequest request, HttpServletResponse response,ModelMap map){
        String state = this.publicService.userLogin(request, response);
        if(state.equals("student")){
            return "redirect:/student/index";
        }
        if(state.equals("teacher")){
            return "redirect:/teacher/index";
        }
        if(state.equals("register")){
            map.put("yblaas",this.publicService.getYblaas());
            return "public/user_login";
        }
        if(state.equals("error")){
            return "redirect:"+this.publicService.getYibanUrl();
        }
        if(state.equals("500")){
            return "redirect:/500.html";
        }
        if(state.equals("403")){
            return "redirect:/403.html";
        }
        return null;
    }

    /**
     * 功能描述:
     * (用户选择注册的类型)
     *
     * @param type 注册的类型
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/3/21 14:04
     */
    @RequestMapping(value = "user_register", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String user_register(String type){
        return this.publicService.userRegister(type);
    }

    /**
     * 功能描述:
     * (查询学院的接口)
     *
     * @return : java.lang.Object
     * @author : xiaozhu
     * @date : 2020/3/24 17:51
     */
    @RequiresUser
    @RequestMapping(value = "get_college_list_ajax", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object get_college_list_ajax(){
        return this.publicService.getCollegeList();
    }

    /**
     * 功能描述:
     * (查询班级的接口)
     *
     * @param collegeId 学院Id
     * @return : java.lang.Object
     * @author : xiaozhu
     * @date : 2020/3/24 21:01
     */
    @RequiresUser
    @RequestMapping(value = "get_eclass_list_ajax", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object get_eclass_list_ajax(Integer collegeId){
        return this.publicService.getEclassList(collegeId);
    }

    /**
     * 功能描述:
     * (发送验证码接口)
     *
     * @param phone 1
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/3/25 23:48
     */
    @RequiresUser
    @RequestMapping(value = "user_send_message", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String user_send_message(String phone){
        return this.publicService.sendMessage(phone);
    }

    /**
     * 功能描述:
     * (发送QQ验证码接口)
     *
     * @param userQq 用户QQ
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/3/28 20:24
     */
    @RequiresUser
    @RequestMapping(value = "user_send_qq", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String user_send_qq(String userQq){
        return this.publicService.sendQq(userQq);
    }

    /**
     * 功能描述:
     * (发送邮箱验证码接口)
     *
     * @param userEmail 用户邮箱
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/3/28 20:29
     */
    @RequiresUser
    @RequestMapping(value = "user_send_email", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String user_send_email(String userEmail){
        return this.publicService.sendEmail(userEmail);
    }

    /**
     * 功能描述:
     * (用户退出系统提示)
     *
     * @param map 页面参数
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/4/14 21:45
     */
    @RequestMapping("user_exit")
    public String user_exit(ModelMap map){
        map.put("yblaas",this.publicService.getYblaas());
        return "public/exit";
    }

    /**
     * 功能描述:
     * (获取系统的站内地址)
     *
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/5/15 11:28
     */
    @RequestMapping(value = "yiban_url", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String yiban_url(){
        return this.publicService.getYibanUrl();
    }

    /**
     * 功能描述:
     * (管理员登陆获取验证码)
     *
     * @param response response
     * @return : void
     * @author : xiaozhu
     * @date : 2020/5/15 11:28
     */
    @RequestMapping("admin_verification")
    public void admin_verification(HttpServletResponse response){
        try {
            response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
            response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expire", 0);
            RandomValidateCodeUtil randomValidateCode = new RandomValidateCodeUtil();
            randomValidateCode.getRandcode(response);//输出验证码图片方法
        } catch (Exception e) {
            logger.error("获取验证码错误，错误信息：", e);
        }
    }

    /*
     * 功能描述:
     * (测试页面)
     *
     * @param map 返回页面参数
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/4/15 16:00
     */
//    @RequestMapping("test")
//    public String test(ModelMap map){
//        try {
//            PropertiesConfiguration conf = new PropertiesConfiguration("db.properties");
//            String demo = conf.getString("spring.datasource.username");
//            System.out.println(demo);
//            System.out.println(conf.getString("spring.datasource.password"));
//            conf.setProperty("spring.datasource.password","123456789");
//            System.out.println(conf.getString("spring.datasource.password"));
//            conf.save();
//        } catch (ConfigurationException e) {
//            e.printStackTrace();
//        }
//        return "public/user_login";
//    }
}
