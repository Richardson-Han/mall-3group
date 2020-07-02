package com.cskaoyan.mall.service.impl;

import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.Collect;
import com.cskaoyan.mall.bean.CollectExample;
import com.cskaoyan.mall.bean.Goods;
import com.cskaoyan.mall.bean.VO.BaseRespVo;
import com.cskaoyan.mall.bean.wx.BO.CollectListBO;
import com.cskaoyan.mall.bean.wx.VO.CollectAddOrDeleteVO;
import com.cskaoyan.mall.bean.wx.VO.CollectListVO;
import com.cskaoyan.mall.bean.wx.VO.CustomCollectVO;
import com.cskaoyan.mall.mapper.CollectMapper;
import com.cskaoyan.mall.mapper.GoodsMapper;
import com.cskaoyan.mall.mapper.UserMapper;
import com.cskaoyan.mall.service.CollectService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/***
 * @author 社会主义好
 * @date 2020-06-27 7:11 星期六 
 *
 */
@Service
public class CollectServiceImpl implements CollectService {

    @Autowired
    CollectMapper collectMapper;

    @Override
    public BaseData queryCollect(Integer page, Integer limit, String sort, String order, Integer userId, Integer valueId) {

        CollectExample collectExample = new CollectExample();
        CollectExample.Criteria criteria = collectExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (valueId != null) {
            criteria.andValueIdEqualTo(valueId);
        }
        collectExample.setOrderByClause(sort + " " + order);
        PageHelper.startPage(page, limit);
        List<Collect> collects = collectMapper.selectByExample(collectExample);
        PageInfo<Collect> collectPageInfo = new PageInfo<>(collects);
        long total = collectPageInfo.getTotal();
        return new BaseData(collects, total);
    }



    //方惠
    //WX中需要添加的
    @Autowired
    UserMapper userMapper;
    @Autowired
    GoodsMapper goodsMapper;

    @Override
    public CollectListVO queryWXCollectList(CollectListBO collectListBO, String username) {
        //根据username来获取对应的userId
        Integer userId = userMapper.selectIdByUsername(username);

        //分页
        Integer page = collectListBO.getPage();
        Integer size = collectListBO.getSize();
        PageHelper.startPage(page, size);
        //看type的值来判断是 商品 还是 专题
        Byte type = collectListBO.getType();
        CollectExample collectExample = new CollectExample();
        if(type == 1){
            //为1 是 专题
            //但是我看专题没有收藏这个按钮，所以这里就不写了
        }else if(type == 0){
            //为0 ，就是商品了
            collectExample.createCriteria().andTypeEqualTo(type).andDeletedEqualTo(false);
        }
        List<Collect> collects = collectMapper.selectByExample(collectExample);
        //此时默认collects中的type都为0
        CustomCollectVO customCollectVO;
        List<CustomCollectVO> collectList = new ArrayList<>();
        for (Collect collect : collects) {
            Integer valueId = collect.getValueId();
            //根据valueId去查找商品
            Goods goods = goodsMapper.selectByPrimaryKey(valueId);
            customCollectVO = new CustomCollectVO(collect.getId(), collect.getValueId(), collect.getType(), goods.getName(),
                    goods.getPicUrl(), goods.getRetailPrice().doubleValue(), goods.getBrief());
            collectList.add(customCollectVO);
        }
        PageInfo<CustomCollectVO> customCollectVOPageInfo = new PageInfo<>(collectList);
        long total = customCollectVOPageInfo.getTotal();

        CollectListVO collectListVO = new CollectListVO(collectList, total);
        return collectListVO;
    }

    /**
     *  商品收藏的取消or添加
     */
    @Override
    public CollectAddOrDeleteVO addordelete(Map map, String username) {
        //根据username来获取对应的userId
        Integer userId = userMapper.selectIdByUsername(username);

        Integer type = (Integer) map.get("type");
        Integer valueId = (Integer) map.get("valueId");
        if(type != 0){
            //type不为0的情况，没有讨论，因为没有专题收藏这个按钮
            return null;
        }
        //此时type=0， 为收藏商品
        //查找collect表中是否已存在该valueId
        Collect collect = collectMapper.selectByValueId(valueId);
        if(collect == null){
            //若不存在，则新添加进去
            Collect collect1 = new Collect(null, userId, valueId, (byte)type.intValue(), new Timestamp(System.currentTimeMillis()),
                    new Timestamp(System.currentTimeMillis()), false);
            collectMapper.insertSelective(collect1);
            return new CollectAddOrDeleteVO("add");
        }else {
            //若存在，判断其deleted的当前值，并修改
            Boolean deleted = collect.getDeleted() == false ? true : false;
            collect.setDeleted(deleted);
            //更新其deleted值
            collectMapper.updateByPrimaryKeySelective(collect);
            if(deleted == true){
                return new CollectAddOrDeleteVO("delete");
            }else
                return new CollectAddOrDeleteVO("add");
        }
    }


}
