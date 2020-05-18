package com.yiban.yblaas.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: yblaas
 * @description: 验证码实体类
 * @author: xiaozhu
 * @create: 2020-03-25 20:51
 **/
@Data
public class Verification implements Serializable {
    private static final long serialVersionUID = -6259195291227668996L;
    private Integer id;
    private String userId;
    private String verification;
    private Date time;
    private String type;
    private String field;
}
