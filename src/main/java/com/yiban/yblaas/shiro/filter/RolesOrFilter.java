package com.yiban.yblaas.shiro.filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @program: yblaas
 * @description: 自定义filter-用户角色是roles中的一个即可 无需同时具备多角色
 * @author: xiaozhu
 * @create: 2020-03-14 10:10
 **/
public class RolesOrFilter extends AuthorizationFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        Subject subject = getSubject(servletRequest,servletResponse);
        String[] roles = (String[]) o;
        if(roles == null || roles.length == 0){
            return false;
        }
        for (String role : roles){
            if(subject.hasRole(role)){
                return true;
            }
        }
        return false;
    }
}
