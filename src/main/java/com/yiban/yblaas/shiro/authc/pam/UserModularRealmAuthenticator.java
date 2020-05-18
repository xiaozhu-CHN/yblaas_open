package com.yiban.yblaas.shiro.authc.pam;

import com.yiban.yblaas.shiro.VirtualType;
import com.yiban.yblaas.shiro.authc.UserToken;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @program: yblaas
 * @description: 自定义shiro的多Realm时候处理的方法
 * @author: xiaozhu
 * @create: 2020-03-13 16:37
 **/
public class UserModularRealmAuthenticator extends ModularRealmAuthenticator {
    private static final Logger logger = LoggerFactory.getLogger(UserModularRealmAuthenticator.class);

    @Override
    protected AuthenticationInfo doAuthenticate(AuthenticationToken authenticationToken){
        try {
            // 判断getRealms()是否返回为空
            assertRealmsConfigured();
            // 强制转换回自定义的CustomizedToken
            UserToken userToken = (UserToken) authenticationToken;
            // 登录类型
            VirtualType virtualType = userToken.getVirtualType();
            // 所有Realm
            Collection<Realm> realms = getRealms();
            // 登录类型对应的所有Realm
            Collection<Realm> typeRealms = new ArrayList<>();
            for (Realm realm : realms) {
                if (realm.getName().contains(virtualType.toString())) {
                    typeRealms.add(realm);
                    // 注：这里使用类名包含枚举，区分realm
                }
            }
            // 判断是单Realm还是多Realm
            if (typeRealms.size() == 1) {
                return doSingleRealmAuthentication(typeRealms.iterator().next(), userToken);
            } else {
                return doMultiRealmAuthentication(typeRealms, userToken);
            }
        } catch (IllegalStateException e) {
            logger.error("自定义多Realm认证错误，错误信息："+e.toString());
            return null;
        }
    }
}
