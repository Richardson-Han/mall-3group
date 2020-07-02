package com.cskaoyan.mall.bean.wx.VO;

import com.cskaoyan.mall.bean.Collect;
import lombok.AllArgsConstructor;
import lombok.CustomLog;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Fang
 * @create 2020/7/2-12:45
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollectListVO {
    List<CustomCollectVO> collectList;
    Long totalPages;
}
