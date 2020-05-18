package com.yiban.yblaas.domain;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: yblaas
 * @description: 学生申请请假实体类
 * @author: xiaozhu
 * @create: 2020-03-22 21:42
 **/
@Data
public class NewLeave implements Serializable {
    private static final long serialVersionUID = 3656784011996208297L;
    private Long id;
    private String student;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date timeStart;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date timeEnd;
    private Integer day;
    private String whereabouts;
    private String cause;
}
