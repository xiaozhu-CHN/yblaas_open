package com.yiban.yblaas.domain;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: yblaas
 * @description: 考勤表实体类
 * @author: xiaozhu
 * @create: 2020-04-05 13:29
 **/
@Data
public class Attendance implements Serializable {
    private static final long serialVersionUID = -1367704974649747679L;
    private Long id; //考勤ID
    private String teacher; //老师ID
    private Integer eclassId; //班级ID
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date timeStart; //开始时间
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date timeEnd; //结束时间
    private Date timeChange; //发起时间
    private String name; //考勤标题
    private String longitude; //经度
    private String latitude; //纬度
    private String beiz; //考勤备注
    private String state; //考勤标记
    private String collegeName; //学院名称
    private String eclassName; //班级名称
    private String teacherName; //老师名称
}
