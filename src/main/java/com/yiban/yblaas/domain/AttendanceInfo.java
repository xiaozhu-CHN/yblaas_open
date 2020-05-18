package com.yiban.yblaas.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: yblaas
 * @description: 考勤记录表实体类
 * @author: xiaozhu
 * @create: 2020-04-07 15:04
 **/
@Data
public class AttendanceInfo implements Serializable {
    private static final long serialVersionUID = -306551623038384865L;
    private Long id; //考勤ID
    private String student; //学生ID
    private Date time; //考勤时间
    private String longitude; //经度
    private String latitude; //纬度
    private String mac; //唯一标识
    private String type; //考勤类型
}
