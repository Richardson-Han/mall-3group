package com.cskaoyan.mall.bean.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 用于统计报表的用户统计
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatBaseVO {
    List<String> columns ;
    List rows;
}
