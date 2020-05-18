package com.yiban.yblaas.shiro.realm;

import com.yiban.yblaas.mapper.PermissionsMapper;
import com.yiban.yblaas.mapper.RolesMapper;
import com.yiban.yblaas.shiro.authc.UserToken;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @program: yblaas
 * @description: 易班账号数据源的Realm
 * @author: xiaozhu
 * @create: 2020-03-13 20:15
 **/
@Component
public class RealmYIBAN extends AuthorizingRealm {

    private static final Logger logger = LoggerFactory.getLogger(RealmYIBAN.class);

    @Autowired
    private RolesMapper rolesMapper;
    @Autowired
    private PermissionsMapper permissionsMapper;

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
        try {
            //易班角色的id
            String yibanId = (String) principalCollection.getPrimaryPrincipal();
            //从缓存中拿到角色数据(没有设置缓存只能再查一次数据库)
            Set<String> roles = getRolesByUserId(yibanId);
            //从缓存中拿到权限数据(没有设置缓存只能再查一次数据库)
            Set<String> permissions = getPermissionUserId(roles);
            //返回对象
            SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
            authorizationInfo.setRoles(roles);
            authorizationInfo.setStringPermissions(permissions);
            return authorizationInfo;
        } catch (Exception e) {
            logger.error("易班用户授权错误，错误信息："+e.toString());
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
            //就是模拟下认证 因为无需认证 只需要授权
            UserToken token = (UserToken) authenticationToken;
            String userId = token.getUsername();
            char[] access_token = token.getPassword();
            if(userId == null || access_token == null){
                return null;
            }
            SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(userId,access_token,"RealmYIBAN");
            return authenticationInfo;
        } catch (Exception e) {
            logger.error("易班用户认证错误，错误信息："+e.toString());
            return null;
        }
    }

    /**
     * 功能描述:
     * (调用Mapper接口获取用户角色信息)
     *
     * @param yibanId 易班用户ID
     * @return : java.util.Set<java.lang.String>
     * @author : xiaozhu
     * @date : 2020/3/13 10:35
     */
    private Set<String> getRolesByUserId(String yibanId) {
        try {
            List<String> roles = rolesMapper.selectRoleName(yibanId);
            Set<String> sets = new HashSet<>(roles);
            return sets;
        } catch (Exception e) {
            logger.error("易班用户从数据库获取角色错误，错误信息："+e.toString());
            return new HashSet<>();
        }
    }

    /**
     * 功能描述:
     * (调用Mapper的接口获取角色的权限信息)
     *
     * @param roles 角色信息
     * @return : java.util.Set<java.lang.String>
     * @author : xiaozhu
     * @date : 2020/3/13 10:41
     */
    private Set<String> getPermissionUserId(Set<String> roles) {
        try {
            Set<String> sets = new HashSet<>();
            for (String role : roles){
                List<String> permission = permissionsMapper.selectPermission(role);
                for (String permis:permission) {
                    sets.add(permis);
                }
            }
            return sets;
        } catch (Exception e) {
            logger.error("易班用户从数据库获取权限信息错误。错误信息："+e.toString());
            return new HashSet<>();
        }
    }
}
