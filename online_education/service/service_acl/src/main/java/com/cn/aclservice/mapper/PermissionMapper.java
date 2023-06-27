package com.cn.aclservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cn.aclservice.domain.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 权限 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
public interface PermissionMapper extends BaseMapper<Permission> {


    List<String> selectPermissionValueByUserId(@Param("userId") String id);

    List<String> selectAllPermissionValue();

    List<Permission> selectPermissionByUserId(@Param("userId") String userId);
}
