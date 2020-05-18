package com.yiban.yblaas.service;

import com.yiban.yblaas.domain.*;

import java.util.List;

/**
 * @program: yblaas
 * @description: 管理员接口
 * @author: xiaozhu
 * @create: 2020-03-14 15:32
 **/
public interface AdminService {
    String adminUpdatePassword(String oldPassword, String newPassword);

    String adminUpdateUser(String newAdminUser, String oldAdminUser, String userPassword);

    DataTables getRolesAll(Integer draw, Integer length, Integer start, Integer column, String dir, String userId);

    String changeUserRoleName(Integer id, String roleName);

    List<Permissions> getPermissionsAll();

    String changePermissions(Integer id, String roleName, String permission);

    String addPermissions(String roleName, String permission);

    String delPermissionsById(Integer id);

    String delPermissionsByIds(Integer[] ids);

    DbConfig getDbConfig();

    String changeDbConfig(DbConfig dbConfig);

    SystemNum getSystemNum(Integer type);

    List<Teacher> getTeacherListXgc();

    String changeTeacherRoleName(String teacherId, String roleName);

    Email getEmail();

    String changeEmail(Email email);

    String demoSendEmail(String sendEmail);
}
