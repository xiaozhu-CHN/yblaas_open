package com.yiban.yblaas.shiro.authc;

import com.yiban.yblaas.shiro.VirtualType;
import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @program: yblaas
 * @description: 自定义Shiro的Token
 * @author: xiaozhu
 * @create: 2020-03-13 16:32
 **/
public class UserToken extends UsernamePasswordToken {
    private VirtualType virtualType;

    public UserToken(final String username, final String password, VirtualType virtualType) {
        super(username, password);
        this.virtualType = virtualType;
    }

    public VirtualType getVirtualType() {
        return virtualType;
    }

    public void setVirtualType(VirtualType virtualType) {
        this.virtualType = virtualType;
    }
}
