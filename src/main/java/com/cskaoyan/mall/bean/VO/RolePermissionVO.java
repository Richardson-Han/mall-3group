package com.cskaoyan.mall.bean.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolePermissionVO {

    List<String> assignedPermissions;

    List<PermissionMenuVO> systemPermissions;
}
