package com.yiban.yblaas.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @program: yblaas
 * @description: 前端表格插件翻页需要封装的数据
 * @author: xiaozhu
 * @create: 2020-03-15 16:33
 **/
@Data
public class DataTables<T> implements Serializable {

    private static final long serialVersionUID = 2968211967942431338L;

    public DataTables(){}

    public DataTables(Integer draw, Long recordsTotal, Long recordsFiltered, List<T> data, String error) {
        this.draw = draw;
        this.recordsTotal = recordsTotal;
        this.recordsFiltered = recordsFiltered;
        this.data = data;
        this.error = error;
    }

    private Integer draw; //Datatables发送的draw是多少那么服务器就返回多少.
    private Long recordsTotal; //即没有过滤的记录数（数据库里总共记录数）
    private Long recordsFiltered; //过滤后的记录数
    private List<T> data; //表中中需要显示的数据。
    private String error; //定义一个错误来描述服务器出了问题后的友好提示
}
