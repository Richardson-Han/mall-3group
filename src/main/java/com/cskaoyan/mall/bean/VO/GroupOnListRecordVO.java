package com.cskaoyan.mall.bean.VO;

import com.cskaoyan.mall.bean.Goods;
import com.cskaoyan.mall.bean.GroupOn;
import com.cskaoyan.mall.bean.GroupOnRules;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于查看团购活动
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupOnListRecordVO {
    private Goods goods;

    private GroupOn groupon;

    private GroupOnRules rules;

    private List<IDsVO> subGroupons;
}


