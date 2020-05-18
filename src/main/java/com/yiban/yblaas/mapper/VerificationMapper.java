package com.yiban.yblaas.mapper;

import com.yiban.yblaas.domain.Verification;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @program: yblaas
 * @description: 验证码表Mapper
 * @author: xiaozhu
 * @create: 2020-03-25 12:58
 **/
@Mapper
@Component
public interface VerificationMapper {
    public Integer insertVerification(String userId, String verification, String type, String field);
    public Verification selectVerification(String userId,String type);
}
