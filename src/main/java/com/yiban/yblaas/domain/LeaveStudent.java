package com.yiban.yblaas.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: yblaas
 * @description: 学生假条的实体类
 * @author: xiaozhu
 * @create: 2020-03-21 13:02
 **/
@Data
public class LeaveStudent implements Serializable {
    private static final long serialVersionUID = -7935945488829129808L;
    private Long id; //假条编号
    private Date timeStart;  //假条开始时间
    private Date timeEnd;  //假条结束时间
    private Date timeChange;  //假条申请的时间
    private Integer day;  //假条的天数
    private String state;  //假条的状态
}
