package com.yiban.yblaas.shiro.authc;

import com.yiban.yblaas.shiro.VirtualType;
import com.yiban.yblaas.shiro.realm.RealmADMIN;
import com.yiban.yblaas.shiro.realm.RealmYIBAN;
import org.apache.shiro.authz.Authorizer;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * @program: yblaas
 * @description: 自定义多授权注入
 * @author: xiaozhu
 * @create: 2020-03-13 21:30
 **/
public class UserModularRealmAuthorizer extends ModularRealmAuthorizer {

    private static final Logger logger = LoggerFactory.getLogger(UserModularRealmAuthorizer.class);

    /**
     * 功能描述:
     * (自定义权限分配的Realm)
     *
     * @param principals 用户ID
     * @param permission 权限名称
     * @return : boolean
     * @author : xiaozhu
     * @date : 2020/4/17 18:54
     */
    @Override
    public boolean isPermitted(PrincipalCollection principals, String permission) {
        try {
            assertRealmsConfigured();
            for (Realm realm : getRealms()) {
                if (!(realm instanceof Authorizer)){ continue;}

                if (realm.getName().contains(VirtualType.ADMIN.toString())) {//类名判断
                    if (permission.contains("admin")){
                        //权限名称是否带admin
                        return ((RealmADMIN) realm).isPermitted(principals, permission);    // 使用改realm的授权方法
                    }
                }
                if (realm.getName().contains(VirtualType.YIBAN.toString())) {
                    //其他的都为易班的用户
                    if (permission.contains("teacher")||permission.contains("student")||permission.contains("fdy")||permission.contains("xyld")||permission.contains("xgc")||permission.contains("black")) {
                        return ((RealmYIBAN) realm).isPermitted(principals, permission);
                    }
                }
            }
            return false;
        } catch (IllegalStateException e) {
            logger.error("用户权限认证匹配错误，错误信息："+e.toString());
            return false;
        }
    }

    //自定义permission匹配 这里系统都是String类型 所以不需要重写
//    @Override
//    public boolean isPermitted(PrincipalCollection principals, Permission permission) {
//        assertRealmsConfigured();
//        for (Realm realm : getRealms()) {
//            if (!(realm instanceof Authorizer)){ continue;}
//
//            if (realm.getName().contains(VirtualType.ADMIN.toString())) {//类名判断
//                return ((RealmADMIN) realm).isPermitted(principals, permission);    // 使用改realm的授权方法
//            }
//            if (realm.getName().contains(VirtualType.YIBAN.toString())) {
//                return ((RealmYIBAN) realm).isPermitted(principals, permission);
//            }
//        }
//        return false;
//    }

    /**
     * 功能描述:
     * (角色获取权限分配Realm)
     *
     * @param principals 用户ID
     * @param roleIdentifier 角色名称
     * @return : boolean
     * @author : xiaozhu
     * @date : 2020/4/17 18:55
     */
    @Override
    public boolean hasRole(PrincipalCollection principals, String roleIdentifier) {
        try {
            assertRealmsConfigured();
            for (Realm realm : getRealms()) {
                if (!(realm instanceof Authorizer)){ continue;}

                if (realm.getName().contains(VirtualType.ADMIN.toString())) {//类名判断
                    if(roleIdentifier.equals("admin")){
                        return ((RealmADMIN) realm).hasRole(principals, roleIdentifier);    // 使用改realm的授权方法
                    }
                }
                if (realm.getName().contains(VirtualType.YIBAN.toString())) {
                    if (roleIdentifier.equals("teacher") || roleIdentifier.equals("student") || roleIdentifier.equals("fdy") || roleIdentifier.equals("xyld") || roleIdentifier.equals("xgc") || roleIdentifier.equals("black")) {
                        return ((RealmYIBAN) realm).hasRole(principals, roleIdentifier);
                    }
                }
            }
            return false;
        } catch (IllegalStateException e) {
            logger.error("用户权限获取匹配Realm错误，错误信息："+e.toString());
            return false;
        }
    }
}
