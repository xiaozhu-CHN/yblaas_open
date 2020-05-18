package com.yiban.yblaas.mapper;

import com.yiban.yblaas.domain.Permissions;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: yblaas
 * @description: 系统角色权限表的接口
 * @author: xiaozhu
 * @create: 2020-03-13 10:32
 **/
@Mapper
@Component
public interface PermissionsMapper {
    public List<String> selectPermission(String roleName);
    public List<Permissions> selectPermissionsAll();
    public Integer updatePermission(Integer id, String roleName, String permission);
    public Integer insertPermission(String roleName, String permission);
    public Integer deletePermissionById(Integer id);
    public Integer deletePermissionByIds(Integer[] ids);
}
