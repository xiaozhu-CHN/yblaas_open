package com.yiban.yblaas.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: yblaas
 * @description: 网站的基本属性实体类
 * @author: xiaozhu
 * @create: 2020-04-14 21:04
 **/
@Data
public class Yblaas implements Serializable {
    private static final long serialVersionUID = -6373812031004157185L;
    private String title;
    private String copyright;
    private String ba;
}
