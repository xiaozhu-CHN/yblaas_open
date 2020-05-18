package com.yiban.yblaas.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: yblaas
 * @description: 权限实体类
 * @author: xiaozhu
 * @create: 2020-03-16 15:02
 **/
@Data
public class Permissions implements Serializable {
    private static final long serialVersionUID = -6765207599404336054L;
    private Integer id; //权限表id
    private String roleName; //角色名称
    private String permission; //权限名称
}
