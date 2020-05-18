package com.yiban.yblaas.shiro.realm;

import com.yiban.yblaas.mapper.DbConfigMapper;
import com.yiban.yblaas.mapper.PermissionsMapper;
import com.yiban.yblaas.mapper.RolesMapper;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @program: yblaas
 * @description: 管理员的Realm
 * @author: xiaozhu
 * @create: 2020-03-13 17:14
 **/
@Component
public class RealmADMIN extends AuthorizingRealm {

    private static final Logger logger = LoggerFactory.getLogger(RealmADMIN.class);

    @Autowired
    private RolesMapper rolesMapper;
    @Autowired
    private PermissionsMapper permissionsMapper;
    @Autowired
    private DbConfigMapper dbConfigMapper;

    /**
     * 功能描述:
     * (重写授权方法)
     *
     * @param principalCollection 1
     * @return : org.apache.shiro.authz.AuthorizationInfo
     * @author : xiaozhu
     * @date : 2020/3/12 22:16
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //管理员角色的id为00000001
        try {
            String adminUser = (String) principalCollection.getPrimaryPrincipal();
            if(!adminUser.equals(dbConfigMapper.selectValue("admin_user"))){
                return null;
            }
            //从缓存中拿到角色数据(没有设置缓存只能再查一次数据库)
            Set<String> roles = getRolesByUserId("00000001");
            //从缓存中拿到权限数据(没有设置缓存只能再查一次数据库)
            Set<String> permissions = getPermissionUserId(roles);
            //返回对象
            SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
            authorizationInfo.setRoles(roles);
            authorizationInfo.setStringPermissions(permissions);
            return authorizationInfo;
        } catch (Exception e) {
            logger.error("管理员权限授权错误，错误信息："+e.toString());
            return null;
        }
    }

    /**
     * 功能描述:
     * (重写认证方法)
     *
     * @param authenticationToken 1
     * @return : org.apache.shiro.authc.AuthenticationInfo
     * @author : xiaozhu
     * @date : 2020/3/12 22:17
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        try {
            //1.从主体传过来的认证信息中获取用户名
            String username = (String) authenticationToken.getPrincipal();
            String userId = dbConfigMapper.selectValue("admin_user");
            String password = dbConfigMapper.selectValue("admin_password");
            if(userId == null || password == null){
                return null;
            }
            SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(userId,password,"RealmADMIN");
            authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(username));
            return authenticationInfo;
        } catch (Exception e) {
            logger.error("管理员认证错误，错误信息"+e.toString());
            return null;
        }
    }

    /**
     * 功能描述:
     * (调用Mapper接口获取用户角色信息)
     *
     * @param adminUser 1
     * @return : java.util.Set<java.lang.String>
     * @author : xiaozhu
     * @date : 2020/3/13 10:35
     */
    private Set<String> getRolesByUserId(String adminUser) {
        try {
            List<String> roles = rolesMapper.selectRoleName(adminUser);
            Set<String> sets = new HashSet<>(roles);
            return sets;
        } catch (Exception e) {
            logger.error("管理员数据库获取角色信息错误，错误信息："+e.toString());
            return new HashSet<>();
        }
    }

    /**
     * 功能描述:
     * (调用Mapper的接口获取角色的权限信息)
     *
     * @param roles 查询角色list
     * @return : java.util.Set<java.lang.String>
     * @author : xiaozhu
     * @date : 2020/3/13 10:41
     */
    private Set<String> getPermissionUserId(Set<String> roles) {
        Set<String> sets = new HashSet<>();
        try {
            for (String role : roles){
                List<String> permission = permissionsMapper.selectPermission(role);
                for (String permis:permission) {
                    sets.add(permis);
                }
            }
            return sets;
        } catch (Exception e) {
            logger.error("管理员权限数据库获取错误，错误信息："+e.toString());
            return sets;
        }
    }
}
