package com.yiban.yblaas.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: yblaas
 * @description: 用于封装当前计算机的属性
 * @author: xiaozhu
 * @create: 2020-03-14 12:02
 **/
@Data
public class SystemPath implements Serializable {

    private static final long serialVersionUID = -1406863223336672679L;

    public SystemPath() {
        this.osName = System.getProperty("os.name");
        this.osArch = System.getProperty("os.arch");
        this.osVersion = System.getProperty("os.version");
        this.lineSeparator = System.getProperty("user.name");
        this.javaVersion = System.getProperty("java.version");
        this.javaHome = System.getProperty("java.home");
        this.javaVmVersion = System.getProperty("java.vm.specification.version");
    }

    private String osName; //操作系统名称
    private String osArch; //操作系统架构
    private String osVersion; //操作系统版本
    private String lineSeparator; //系统用户账户名称
    private String javaVersion; //java版本
    private String javaHome; //java安装目录
    private String javaVmVersion; //java虚拟机版本
}
