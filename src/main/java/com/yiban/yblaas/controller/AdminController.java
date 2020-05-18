package com.yiban.yblaas.controller;

import com.yiban.yblaas.domain.*;
import com.yiban.yblaas.service.impl.AdminServiceImpl;
import com.yiban.yblaas.service.impl.PublicServiceImpl;
import net.sf.json.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
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
 * @description: 超级管理员Controller
 * @author: xiaozhu
 * @create: 2020-03-14 14:06
 **/
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminServiceImpl adminService;
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
        map.put("systemPath",new SystemPath());
        map.put("yblaas",this.publicService.getYblaas());
        map.put("zongshu",this.adminService.getSystemNum(1));
        map.put("jinri",this.adminService.getSystemNum(2));
        map.put("zuori",this.adminService.getSystemNum(3));
        map.put("benzhou",this.adminService.getSystemNum(4));
        map.put("benyue",this.adminService.getSystemNum(5));
        return "admin/welcome";
    }

    /**
     * 功能描述:
     * (首页)
     *
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/3/14 16:17
     */
    @RequestMapping("index")
    public String index(ModelMap map){
        map.put("yblaas",this.publicService.getYblaas());
        return "admin/index";
    }

    /**
     * 功能描述:
     * (超级管理员修改密码)
     *
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/3/14 16:18
     */
    @RequestMapping(value = "change_password", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String change_password(String oldPassword, String newPassword){
        return this.adminService.adminUpdatePassword(oldPassword, newPassword);
    }

    /**
     * 功能描述:
     * (超级管理员退出系统)
     *
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/3/15 15:23
     */
    @RequestMapping("exit")
    public String exit(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/public/admin_login";
    }

    /**
     * 功能描述:
     * (超级管理员修改用户名)
     *
     * @param adminUser 修改后的用户名
     * @param userPassword 超级管理员密码
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/3/15 15:23
     */
    @RequestMapping(value = "change_user", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String change_user(String adminUser, String userPassword){
        Session session = SecurityUtils.getSubject().getSession();
        String msg = this.adminService.adminUpdateUser(adminUser, (String) session.getAttribute("adminUser"), userPassword);
        if(msg.equals("success")){
            session.setAttribute("adminUser",adminUser);
        }
        return msg;
    }

    /**
     * 功能描述:
     * (用户管理的角色管理的页面)
     *
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/3/15 15:26
     */
    @RequestMapping("user_role")
    public String user_role(){
        return "admin/admin/user_role";
    }

    /**
     * 功能描述:
     * (角色管理的数据查询和搜索)
     *
     * @param draw Datatables发送的draw
     * @param length 每页的数量
     * @param start 开始的数量
     * @param column 角色的字段（0开始）
     * @param dir 排序方式
     * @param userId 易班Id
     * @return : java.lang.Object
     * @author : xiaozhu
     * @date : 2020/3/15 21:47
     */
    @RequestMapping(value = "user_role_ajax", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object user_role_ajax(Integer draw, Integer length, Integer start, Integer column, String dir, String userId){
        if(userId == null || userId.equals("")){
            userId = null;
        }
        DataTables<Roles> dataTables = this.adminService.getRolesAll(draw, length, start, column, dir, userId);
        return dataTables;
    }

    /**
     * 功能描述:
     * (修改用户的角色)
     *
     * @param id 用户的ID(非易班ID)
     * @param roleName 用户的角色名称
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/3/16 12:52
     */
    @RequestMapping(value = "change_user_role_name", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String change_user_role_name(Integer id, String roleName){
        return this.adminService.changeUserRoleName(id, roleName);
    }

    /**
     * 功能描述:
     * (用户管理中的权限管理页面)
     *
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/3/16 15:17
     */
    @RequestMapping("user_permission")
    public String user_permission(){
        return "admin/admin/user_permission";
    }

    /**
     * 功能描述:
     * (查询所有权限信息的ajax)
     *
     * @return : java.lang.Object
     * @author : xiaozhu
     * @date : 2020/3/16 20:48
     */
    @RequestMapping(value = "user_permission_ajax", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object user_permission_ajax(){
        JSONObject jsonObject = new JSONObject();
        //需要再封装一层data
        jsonObject.put("data",this.adminService.getPermissionsAll());
        return jsonObject.toString();
    }

    /**
     * 功能描述:
     * (超级管理员修改角色权限信息)
     *
     * @param id id
     * @param roleName 角色
     * @param permission 权限
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/3/16 22:32
     */
    @RequestMapping(value = "change_user_permission", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String change_user_permission(Integer id, String roleName, String permission){
        return this.adminService.changePermissions(id, roleName, permission);
    }

    /**
     * 功能描述:
     * (新增角色权限信息)
     *
     * @param roleName 角色名称
     * @param permission 权限名称
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/3/16 23:08
     */
    @RequestMapping(value = "add_user_permission", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String add_user_permission(String  roleName, String permission){
        return this.adminService.addPermissions(roleName, permission);
    }

    /**
     * 功能描述:
     * (删除单个角色权限信息)
     *
     * @param id id
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/3/16 23:34
     */
    @RequestMapping(value = "del_user_permission", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String del_user_permission(Integer id){
        return this.adminService.delPermissionsById(id);
    }

    /**
     * 功能描述:
     * (超级管理员批量删除权限信息)
     *
     * @param ids ids
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/3/17 18:09
     */
    @RequestMapping(value = "del_user_permissions", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String del_user_permissions(@RequestParam(value = "ids[]")Integer[] ids){
        return this.adminService.delPermissionsByIds(ids);
    }

    /**
     * 功能描述:
     * (超级管理员系统设置页)
     *
     * @param map 往前台传参
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/3/17 23:10
     */
    @RequestMapping("system_base")
    public String system_base(ModelMap map){
        map.put("dbConfig",this.adminService.getDbConfig());
        return "admin/system/system_base";
    }

    /**
     * 功能描述:
     * (超级管理员修改系统设置)
     *
     * @param dbConfig 系统设置实体类
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/3/17 23:11
     */
    @RequestMapping(value = "system_base_ajax", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String system_base_ajax(DbConfig dbConfig){
        return this.adminService.changeDbConfig(dbConfig);
    }

    /**
     * 功能描述:
     * (超级管理员任命学工页面)
     *
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/4/24 23:57
     */
    @RequestMapping("user_xgc")
    public String user_xgc(){
        return "admin/admin/user_xgc";
    }

    /**
     * 功能描述:
     * (超级管理员学工页面老师数据查询)
     *
     * @return : java.lang.Object
     * @author : xiaozhu
     * @date : 2020/4/25 0:01
     */
    @RequestMapping(value = "user_xgc_ajax", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object user_xgc_ajax(){
       return this.adminService.getTeacherListXgc();
    }

    /**
     * 功能描述:
     * (超级管理员任命学工处)
     *
     * @param teacherId 老师ID
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/4/25 0:17
     */
    @RequestMapping(value = "user_xgc_add", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String user_xgc_add(String teacherId){
        return this.adminService.changeTeacherRoleName(teacherId, "xgc");
    }

    /**
     * 功能描述:
     * (超级管理员取消任命学工处)
     *
     * @param teacherId 老师ID
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/4/25 0:19
     */
    @RequestMapping(value = "user_xgc_del", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String user_xgc_del(String teacherId){
        return this.adminService.changeTeacherRoleName(teacherId, "teacher");
    }

    /**
     * 功能描述:
     * (系统设置邮箱页面)
     *
     * @param map 传页面参数
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/5/17 21:00
     */
    @RequestMapping("system_email")
    public String system_email(ModelMap map){
        map.put("email",this.adminService.getEmail());
        return "admin/system/system_email";
    }

    /**
     * 功能描述:
     * (修改系统邮件设置)
     *
     * @param email 邮件实体类
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/5/17 21:09
     */
    @RequestMapping(value = "system_email_ajax", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String system_email_ajax(Email email){
        return this.adminService.changeEmail(email);
    }

    /**
     * 功能描述:
     * (发送系统测试邮件)
     *
     * @param sendEmail 收信邮箱
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/5/17 21:55
     */
    @RequestMapping(value = "system_send_email_ajax", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String system_send_email_ajax(String sendEmail){
        return this.adminService.demoSendEmail(sendEmail);
    }
}
