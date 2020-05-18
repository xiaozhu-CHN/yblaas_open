package com.yiban.yblaas.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: yblaas
 * @description: 学生考勤的实体类
 * @author: xiaozhu
 * @create: 2020-04-06 21:27
 **/
@Data
public class AttendanceStudent implements Serializable {
    private static final long serialVersionUID = -8592547974834825721L;
    private Long id; //考勤ID
    private Date timeStart; //开始时间
    private Date timeEnd; //结束时间
    private Date timeChange; //发起时间
    private String name; //考勤标题
}
