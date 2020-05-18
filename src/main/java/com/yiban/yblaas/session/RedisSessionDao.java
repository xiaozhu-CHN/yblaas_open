package com.yiban.yblaas.session;

import com.yiban.yblaas.util.RedisUtil;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Component
public class RedisSessionDao extends AbstractSessionDAO {

    private static final Logger logger = LoggerFactory.getLogger(RedisSessionDao.class);

    @Autowired
    private RedisUtil redisUtil;

    //自定义key的前缀，方面后面查询
    private final String SHIRO_SESSION_PREFIX = "shiro-session:";

    private String getkey(String key){
        //返回key
        return SHIRO_SESSION_PREFIX + key;
    }

    private void saveSession(Session session){
        try {
            //保存session方法
            if(session != null || session.getId() != null){
                String key = getkey(session.getId().toString());
                redisUtil.set(key, session);
                redisUtil.expire(key,600);
            }
        } catch (Exception e) {
            logger.error("session缓存Redis错误，错误信息："+e.toString());
        }
    }

    @Override
    protected Serializable doCreate(Session session) {
        //插入session
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session,sessionId);
        saveSession(session);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        try {
            //获得session
            if(sessionId == null ){
                return null;
            }
            String key = getkey(sessionId.toString());
            Session value = (Session) redisUtil.get(key);
            if(value == null){
                return null;
            }
            return value;
        } catch (Exception e) {
            logger.error("获取session缓存错误，错误信息："+e.toString());
            return null;
        }
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        //更新session
        saveSession(session);
    }

    @Override
    public void delete(Session session) {
        try {
            if(session == null || session.getId() == null){
                return;
            }
            String key = getkey(session.getId().toString());
            redisUtil.del(key);
        } catch (Exception e) {
            logger.error("删除session缓存错误，错误信息："+e.toString());
        }
    }

    @Override
    public Collection<Session> getActiveSessions() {
        try {
            Set<String> keys = redisUtil.keys(SHIRO_SESSION_PREFIX);
            Set<Session> sessions = new HashSet<>();
            if(CollectionUtils.isEmpty(keys)){
                //如果为空的集合就直接返回
                return sessions;
            }
            //如果不是就遍历
            for(String key : keys){
                Session session = (Session) redisUtil.get(key);
                sessions.add(session);
            }
            return sessions;
        } catch (Exception e) {
            logger.error("查询所有的session错误，错误信息："+e.toString());
            return new HashSet<>();
        }
    }
}
