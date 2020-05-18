package com.yiban.yblaas.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: yblaas
 * @description: 考勤结果页面的辅助参数实体类
 * @author: xiaozhu
 * @create: 2020-04-06 10:40
 **/
@Data
public class AttendanceInfoData implements Serializable {
    private static final long serialVersionUID = 5109567117996607213L;
    private Integer eclassNumber; //班级学生总数
    private Integer attendanceNumber; //已考勤总数
    private Integer attendanceNumberNo; //未考勤人数
    private String longitude; //考勤经度
    private String latitude; //考勤纬度
    private String attendanceAccuracy; //考勤精度
}
