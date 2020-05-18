package com.yiban.yblaas.session;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.session.mgt.WebSessionKey;

import javax.servlet.ServletRequest;
import java.io.Serializable;

public class CustomSessionManager extends DefaultWebSessionManager {
    @Override
    protected Session retrieveSession(SessionKey sessionKey) throws UnknownSessionException {
        Serializable sessionId = getSessionId(sessionKey);
        ServletRequest request = null;
        if(sessionKey instanceof WebSessionKey){
            //判断sessionKey是不是WebSessionKey是的话才能强转
            request = ((WebSessionKey)sessionKey).getServletRequest();
        }
        if(request != null && sessionId !=null){
            //如果request不为空获取request的session
            Session session = (Session) request.getAttribute(sessionId.toString());
            if(session != null){
                return session;
            }
        }
        //为空就再获取一次
        Session session = super.retrieveSession(sessionKey);
        if(request != null && sessionId != null){
            //获取request的session
            request.setAttribute(sessionId.toString(),session);
        }
        return session;
    }
}
