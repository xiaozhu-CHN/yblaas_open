package com.yiban.yblaas.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @program: yblaas
 * @description: 配置表的Mapper
 * @author: xiaozhu
 * @create: 2020-03-12 11:48
 **/
@Mapper
@Component  //预防idea IDE编辑器报错
public interface DbConfigMapper {
    public String selectValue(String ckey);
    public Integer selectKey(String ckey);
    public Integer updateValue(String ckey, String cvalue);
}
