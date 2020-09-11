package com.yiban.yblaas.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yiban.yblaas.domain.*;
import com.yiban.yblaas.mapper.*;
import com.yiban.yblaas.service.AdminService;
import com.yiban.yblaas.thread.EmailThread;
import com.yiban.yblaas.util.MessageUtil;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @program: yblaas
 * @description: 管理员接口实现类
 * @author: xiaozhu
 * @create: 2020-03-14 15:33
 **/
@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    private static final Logger logger = LoggerFactory.getLogger(AdminService.class);

    @Autowired
    private DbConfigMapper dbConfigMapper;
    @Autowired
    private RolesMapper rolesMapper;
    @Autowired
    private PermissionsMapper permissionsMapper;
    @Autowired
    private LeaveMapper leaveMapper;
    @Autowired
    private AttendanceMapper attendanceMapper;
    @Autowired
    private AttendanceInfoMapper attendanceInfoMapper;
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private MessageUtil messageUtil;


    /**
     * 功能描述:
     * (修改超级管理员的密码)
     *
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/3/14 15:38
     */
    @Override
    public String adminUpdatePassword(String oldPassword, String newPassword) {
        try {
            String adminUser = dbConfigMapper.selectValue("admin_user");
            String adminPassword = dbConfigMapper.selectValue("admin_password");
            if(adminPassword.equals(new Md5Hash(oldPassword,adminUser).toString())){
                Integer number = dbConfigMapper.updateValue("admin_password", new Md5Hash(newPassword, adminUser).toString());
                if(number == 1){
                    return "success";
                }else{
                    logger.warn("超级管理员修改密码对数据库更改失败！");
                    return "error";
                }
            }else {
                return "error";
            }
        } catch (Exception e) {
            logger.error("修改超级管理员的密码错误，错误信息："+e.toString());
            return "error";
        }
    }

    /**
     * 功能描述:
     * (超级管理员修改用户名)
     *
     * @param newAdminUser 新用户名
     * @param oldAdminUser 旧用户名
     * @param userPassword 用户密码
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/3/14 20:23
     */
    @Override
    public String adminUpdateUser(String newAdminUser, String oldAdminUser, String userPassword) {
        try {
            String adminPassword = dbConfigMapper.selectValue("admin_password");
            if(adminPassword.equals(new Md5Hash(userPassword,oldAdminUser).toString())){
                Integer numberOne = this.dbConfigMapper.updateValue("admin_user", newAdminUser);
                Integer numberTwo = dbConfigMapper.updateValue("admin_password", new Md5Hash(userPassword, newAdminUser).toString());
                if(numberOne == 1 && numberTwo == 1){
                    return "success";
                }else {
                    logger.warn("超级管理员修改用户名对数据库更改失败！");
                    return "error";
                }
            }else{
                return "error";
            }
        } catch (Exception e) {
            logger.error("超级管理员修改用户名错误，错误信息："+e.toString());
            return "error";
        }
    }

    /**
     * 功能描述:
     * (超级管理员查询用户角色)
     *
     * @param draw Datatables发送的draw
     * @param length 每页的数量
     * @param start 开始的数量
     * @param column 角色的字段（0开始）
     * @param dir 排序方式
     * @param userId 易班Id
     * @return : com.yiban.yblaas.domain.DataTables
     * @author : xiaozhu
     * @date : 2020/3/15 19:06
     */
    @Override
    public DataTables getRolesAll(Integer draw, Integer length, Integer start, Integer column, String dir, String userId) {
        try {
            PageHelper.startPage(start,length);
            List<Roles> rolesList = this.rolesMapper.selectRolesAll(column, dir, userId);
            PageInfo<Roles> pageInfo = new PageInfo<Roles>(rolesList);
            Long recordsTotal = this.rolesMapper.selectRolesAllNum();
            return new DataTables(draw, recordsTotal, (Long) pageInfo.getTotal(), pageInfo.getList(), null);
        } catch (Exception e) {
            logger.error("超级管理员查询用户角色，错误信息："+e.toString());
            return new DataTables(draw, null, null, null, "服务器错误");
        }
    }

    /**
     * 功能描述:
     * (修改用户的角色名称)
     *
     * @param id 用户id（非易班id）
     * @param roleName 更改的用户的角色名称
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/3/16 12:52
     */
    @Override
    public String changeUserRoleName(Integer id, String roleName) {
        try {
            if(this.rolesMapper.updateRoleName(id, roleName) == 1){
                return "success";
            }else {
                return "error";
            }
        } catch (Exception e) {
            logger.error("修改用户的角色名称错误，错误信息："+e.toString());
            return "error";
        }
    }

    /**
     * 功能描述:
     * (查询所有用户的权限)
     *
     * @return : java.util.List<com.yiban.yblaas.domain.Permissions>
     * @author : xiaozhu
     * @date : 2020/3/16 20:48
     */
    @Override
    public List<Permissions> getPermissionsAll() {
        try {
            return this.permissionsMapper.selectPermissionsAll();
        } catch (Exception e) {
            logger.error("查询所有用户的权限错误，错误信息："+e.toString());
            return null;
        }
    }

    /**
     * 功能描述:
     * (修改角色权限的方法)
     *
     * @param id id
     * @param roleName 角色
     * @param permission 权限
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/3/16 22:30
     */
    @Override
    public String changePermissions(Integer id, String roleName, String permission) {
        try {
            if(this.permissionsMapper.updatePermission(id, roleName, permission) == 1){
                return "success";
            }else{
                return "error";
            }
        } catch (Exception e) {
            logger.error("修改角色权限的方法错误，错误信息："+e.toString());
            return "error";
        }
    }

    /**
     * 功能描述:
     * (新增角色权限信息)
     *
     * @param roleName 角色名称
     * @param permission 权限名称
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/3/16 22:32
     */
    @Override
    public String addPermissions(String roleName, String permission) {
        try {
            if(this.permissionsMapper.insertPermission(roleName, permission) == 1){
                return "success";
            }else{
                return "error";
            }
        } catch (Exception e) {
            logger.error("新增角色权限信息错误，错误信息："+e.toString());
            return "error";
        }
    }

    /**
     * 功能描述:
     * (删除角色权限信息)
     *
     * @param id id
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/3/16 23:07
     */
    @Override
    public String delPermissionsById(Integer id){
        try {
            if(this.permissionsMapper.deletePermissionById(id) == 1){
                return "success";
            }else{
                return "error";
            }
        } catch (Exception e) {
            logger.error("删除角色权限信息错误，错误信息："+e.toString());
            return "error";
        }
    }

    /**
     * 功能描述:
     * (批量删除角色信息)
     *
     * @param ids ids
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/3/17 0:01
     */
    @Override
    public String delPermissionsByIds(Integer[] ids) {
        try {
            if(this.permissionsMapper.deletePermissionByIds(ids) >=1){
                return "success";
            }else{
                return "error";
            }
        } catch (Exception e) {
            logger.error("批量删除角色权限信息错误，错误信息："+e.toString());
            return "error";
        }
    }

    /**
     * 功能描述:
     * (查询系统设置)
     *
     * @return : com.yiban.yblaas.domain.DbConfig
     * @author : xiaozhu
     * @date : 2020/3/17 18:36
     */
    @Override
    public DbConfig getDbConfig() {
        try {
            DbConfig dbConfig = new DbConfig();
            dbConfig.setYblaasTitle(dbConfigMapper.selectValue("yblaas_title"));
            dbConfig.setYblaasCopyright(dbConfigMapper.selectValue("yblaas_copyright"));
            dbConfig.setYblaasBa(dbConfigMapper.selectValue("yblaas_ba"));
            dbConfig.setYibanAppId(dbConfigMapper.selectValue("yiban_appId"));
            dbConfig.setYibanAppSecret(dbConfigMapper.selectValue("yiban_appsecret"));
            dbConfig.setYibanUrl(dbConfigMapper.selectValue("yiban_url"));
            dbConfig.setYibanSchool(dbConfigMapper.selectValue("yiban_school"));
            dbConfig.setYibanThis(dbConfigMapper.selectValue("yiban_this"));
            dbConfig.setLeaveXyld(dbConfigMapper.selectValue("leave_xyld"));
            dbConfig.setLeaveXgc(dbConfigMapper.selectValue("leave_xgc"));
            dbConfig.setAttendanceAccuracy(dbConfigMapper.selectValue("attendance_accuracy"));
            dbConfig.setGaodeKey(dbConfigMapper.selectValue("gaode_key"));
            return dbConfig;
        } catch (Exception e) {
            logger.error("查询系统设置错误，错误信息："+e.toString());
            return null;
        }
    }

    /**
     * 功能描述:
     * (保存系统设置)
     *
     * @param dbConfig 系统设置实体类
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/3/17 19:54
     */
    @Override
    public String changeDbConfig(DbConfig dbConfig) {
        try {
            dbConfigMapper.updateValue("yblaas_title", (dbConfig.getYblaasTitle()==null || dbConfig.getYblaasTitle().equals(""))?null:dbConfig.getYblaasTitle());
            dbConfigMapper.updateValue("yblaas_copyright", (dbConfig.getYblaasCopyright()==null || dbConfig.getYblaasCopyright().equals(""))?null:dbConfig.getYblaasCopyright());
            dbConfigMapper.updateValue("yblaas_ba", (dbConfig.getYblaasBa()==null || dbConfig.getYblaasBa().equals(""))?null:dbConfig.getYblaasBa());
            dbConfigMapper.updateValue("yiban_appId", (dbConfig.getYibanAppId()==null || dbConfig.getYibanAppId().equals(""))?null:dbConfig.getYibanAppId());
            dbConfigMapper.updateValue("yiban_appsecret", (dbConfig.getYibanAppSecret()==null || dbConfig.getYibanAppSecret().equals(""))?null:dbConfig.getYibanAppSecret());
            dbConfigMapper.updateValue("yiban_url", (dbConfig.getYibanUrl()==null || dbConfig.getYibanUrl().equals(""))?null:dbConfig.getYibanUrl());
            dbConfigMapper.updateValue("yiban_school", (dbConfig.getYibanSchool()==null || dbConfig.getYibanSchool().equals(""))?null:dbConfig.getYibanSchool());
            dbConfigMapper.updateValue("yiban_this", (dbConfig.getYibanThis()==null || dbConfig.getYibanThis().equals(""))?null:dbConfig.getYibanThis());
            dbConfigMapper.updateValue("leave_xyld", (dbConfig.getLeaveXyld()==null || dbConfig.getLeaveXyld().equals(""))?null:dbConfig.getLeaveXyld());
            dbConfigMapper.updateValue("leave_xgc", (dbConfig.getLeaveXgc()==null || dbConfig.getLeaveXgc().equals(""))?null:dbConfig.getLeaveXgc());
            dbConfigMapper.updateValue("attendance_accuracy", (dbConfig.getAttendanceAccuracy()==null || dbConfig.getAttendanceAccuracy().equals(""))?null:dbConfig.getAttendanceAccuracy());
            dbConfigMapper.updateValue("gaode_key", (dbConfig.getGaodeKey()==null || dbConfig.getGaodeKey().equals(""))?null:dbConfig.getGaodeKey());
            return "success";
        } catch (Exception e) {
            logger.error("保存系统设置错误，错误信息："+e.toString());
            return "error";
        }
    }

    /**
     * 功能描述:
     * (管理员查询欢迎页信息)
     *
     * @param type 查询类型
     * @return : com.yiban.yblaas.domain.SystemNum
     * @author : xiaozhu
     * @date : 2020/4/23 20:44
     */
    @Override
    public SystemNum getSystemNum(Integer type) {
        try {
            SystemNum systemNum = new SystemNum();
            systemNum.setLeaveNum(this.leaveMapper.selectLeaveNum(type));
            systemNum.setAttendanceNum(this.attendanceMapper.selectAttendanceNum(type));
            systemNum.setAttendanceInfoNum(this.attendanceInfoMapper.selectAttendanceInfoNum(type));
            if(type == 2 || type == 3){
                systemNum.setLeaveNowNum(this.leaveMapper.selectLeaveNowNum(type));
            }
            return systemNum;
        } catch (Exception e) {
            logger.error("管理员查询数据信息错误，错误信息："+e.toString());
            return null;
        }
    }

    /**
     * 功能描述:
     * (管理员查询学工任命老师数据)
     *
     * @return : java.util.List<com.yiban.yblaas.domain.Teacher>
     * @author : xiaozhu
     * @date : 2020/4/24 23:52
     */
    @Override
    public List<Teacher> getTeacherListXgc() {
        try {
            return this.teacherMapper.selectTeacherByXgc();
        } catch (Exception e) {
            logger.error("管理员查询学工任命数据错误，错误信息："+e.toString());
            return null;
        }
    }

    /**
     * 功能描述:
     * (管理员修改学工任命)
     *
     * @param teacherId 老师ID
     * @param roleName 角色名称
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/4/25 0:14
     */
    @Override
    public String changeTeacherRoleName(String teacherId, String roleName) {
        try {
            Roles roles = this.rolesMapper.selectRoles(teacherId);
            if(this.rolesMapper.updateRoleName(roles.getId(), roleName) ==1){
                return "success";
            }else {
                return "error";
            }
        } catch (Exception e) {
            logger.error("管理员修改学工任命错误，错误信息："+e.toString());
            return "error";
        }
    }

    /**
     * 功能描述:
     * (查询系统邮件配置)
     *
     * @return : com.yiban.yblaas.domain.Email
     * @author : xiaozhu
     * @date : 2020/5/17 20:59
     */
    @Override
    public Email getEmail() {
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
            logger.error("查询系统的邮件配置信息错误，错误信息：",e);
            return null;
        }
    }

    /**
     * 功能描述:
     * (修改系统邮件设置)
     *
     * @param email 1
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/5/17 21:08
     */
    @Override
    public String changeEmail(Email email) {
        try {
            dbConfigMapper.updateValue("email", (email.getEmail()==null || email.getEmail().equals(""))?null:email.getEmail());
            dbConfigMapper.updateValue("email_call", (email.getEmailCall()==null || email.getEmailCall().equals(""))?null:email.getEmailCall());
            dbConfigMapper.updateValue("email_host", (email.getEmailHost()==null || email.getEmailHost().equals(""))?null:email.getEmailHost());
            dbConfigMapper.updateValue("email_name",  (email.getEmailName()==null || email.getEmailName().equals(""))?null:email.getEmailName());
            dbConfigMapper.updateValue("email_password", (email.getEmailPassword()==null || email.getEmailPassword().equals(""))?null:email.getEmailPassword());
            dbConfigMapper.updateValue("email_port", (email.getEmailPort()==null || email.getEmailPort().equals(""))?null:email.getEmailPort());
            return "success";
        } catch (Exception e) {
            logger.error("修改系统邮件设置错误，错误信息：",e);
            return "error";
        }
    }

    /**
     * 功能描述:
     * (管理员发送测试邮件)
     *
     * @param sendEmail 收信邮箱
     * @return : java.lang.String
     * @author : xiaozhu
     * @date : 2020/5/17 21:54
     */
    @Override
    public String demoSendEmail(String sendEmail) {
        try {
            String yblaasTitle = this.dbConfigMapper.selectValue("yblaas_title");
            if(messageUtil.Email(sendEmail, yblaasTitle, 0, null)){
                return "success";
            }else {
                return "error";
            }
        } catch (Exception e) {
            logger.error("管理员发送测试邮件错误，错误信息：",e);
            return "error";
        }
    }
}
