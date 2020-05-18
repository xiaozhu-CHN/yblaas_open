package com.yiban.yblaas.mapper;

import com.yiban.yblaas.domain.Roles;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: yblaas
 * @description: 用户角色接口
 * @author: xiaozhu
 * @create: 2020-03-13 10:15
 **/
@Mapper
@Component  //预防idea IDE编辑器报错
public interface RolesMapper {
    public List<String> selectRoleName(String userId);
    public List<Roles> selectRolesAll(Integer column, String dir, String userId);
    public Long selectRolesAllNum();
    public Integer updateRoleName(Integer id, String roleName);
    public Integer insertRoles(String userId, String roleName);
    public Roles selectRoles(String userId);
}
