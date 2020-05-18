package com.yiban.yblaas.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: yblaas
 * @description: 管理员数据统计类
 * @author: xiaozhu
 * @create: 2020-04-21 22:03
 **/
@Data
public class SystemNum implements Serializable {
    private static final long serialVersionUID = 4602183420793906444L;
    private Long leaveNum; //假条数量
    private Long leaveNowNum; //休假
    private Long attendanceNum; //考勤数量
    private Integer attendanceInfoNum; //参与考勤人数
}
