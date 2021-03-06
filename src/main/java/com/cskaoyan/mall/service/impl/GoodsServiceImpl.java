package com.cskaoyan.mall.service.impl;

import com.cskaoyan.mall.bean.BO.BrandBO;
import com.cskaoyan.mall.bean.BO.GoodsListBO;
import com.cskaoyan.mall.bean.BO.GoodsUpdateBO;
import com.cskaoyan.mall.bean.GoodsExample;
import com.cskaoyan.mall.bean.GoodsStat;
import com.cskaoyan.mall.bean.VO.*;
import com.cskaoyan.mall.bean.VO.wx.WXCommentVO;
import com.cskaoyan.mall.bean.wx.WXFloorGoods;
import com.cskaoyan.mall.mapper.GoodsMapper;
import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.mapper.*;
import com.cskaoyan.mall.service.GoodsService;
import com.cskaoyan.mall.utils.CharacterArrayConversionUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.System;
import java.text.SimpleDateFormat;
import java.util.*;

/***
 * @author 社会主义好
 * @date 2020-06-26 17:23 星期五
 *
 */
@Service
public class GoodsServiceImpl implements GoodsService {
    //多个mapper一定要分开写
    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    GoodsAttributeMapper goodsAttributeMapper;
    @Autowired
    GoodsCategoryMapper categoryMapper;
    @Autowired
    GoodsProductMapper productMapper;
    @Autowired
    GoodsSpecMapper specMapper;
    @Autowired
    GoodsBrandMapper brandMapper;

    //尚政宇
    @Autowired
    CategoryMapper wxCategoryMapper;

    /*@Override
    public Integer selectLastId() {
        return goodsMapper.selectLastId();
    }*/

    /**
     * 返回商品数量
     */
    @Override
    public Long getGoodsTotal() {
        return goodsMapper.countByExample(new GoodsExample());
    }

    /**
     * 首页返回货品数量
     */
    @Override
    public Long getProductTotal() {
        return productMapper.countByExample(new GoodsProductExample());
    }


    /**
     * 显示全部商品信息
     * or 查询商品信息
     */
    @Override
    public BaseData queryGoods(GoodsListBO goodsListBO) {
        Integer page = goodsListBO.getPage();
        Integer limit = goodsListBO.getLimit();
        String sort = goodsListBO.getSort();
        String order = goodsListBO.getOrder();

        GoodsExample goodsExample = new GoodsExample();
        goodsExample.setOrderByClause(sort + " " + order);
        PageHelper.startPage(page, limit);
        List<Goods> goods = new ArrayList<>();
        long total = 0;
        if (goodsListBO.getGoodsSn() == null && goodsListBO.getName() == null) {

        } else if (goodsListBO.getGoodsSn() == null && goodsListBO.getName() != null) {
            goodsExample.createCriteria().andNameLike("%" + goodsListBO.getName() + "%");
        } else if (goodsListBO.getGoodsSn() != null && goodsListBO.getName() == null) {
            goodsExample.createCriteria().andIdEqualTo(goodsListBO.getGoodsSn());
        } else {
            goodsExample.createCriteria().andIdEqualTo(goodsListBO.getGoodsSn()).andNameLike("%" + goodsListBO.getName() + "%");
        }
        //注意虚拟删除
        goodsExample.createCriteria().andDeletedEqualTo(false);
        goods = goodsMapper.selectByExampleWithBLOBs(goodsExample);
        PageInfo<Goods> goodsPageInfo = new PageInfo<Goods>(goods);
        total = goodsPageInfo.getTotal();
        return new BaseData(goods, total);
    }

    /**
     * 查看商品详情
     */
    @Override
    public GoodsDetailVO queryGoods(Integer id) {
        //获取商品id对应的属性集合（attribute表）
        GoodsAttributeExample goodsAttributeExample = new GoodsAttributeExample();
        goodsAttributeExample.createCriteria().andGoodsIdEqualTo(id).andDeletedEqualTo(false);
        List<GoodsAttribute> attributes = goodsAttributeMapper.selectByExample(goodsAttributeExample);

        //获取商品id信息，以及对应的主类别id，再获取全部的类别集合（category表中）
        //GoodsExample goodsExample = new GoodsExample();
        Goods goods = goodsMapper.selectByPrimaryKey(id);

        //获取子类别id，来获取父类别id
        Integer categoryId = goods.getCategoryId();
        GoodsCategory goodsCategory = categoryMapper.selectByPrimaryKey(categoryId);
        List<Object> categoryIds = new ArrayList<>();
        if (goodsCategory.getPid() != null) {
            categoryIds.add(goodsCategory.getPid());
        }
        categoryIds.add(goodsCategory.getId());

        //通过商品id来获取其products信息（product表）
        GoodsProductExample productExample = new GoodsProductExample();
        productExample.createCriteria().andGoodsIdEqualTo(id).andDeletedEqualTo(false);
        List<GoodsProduct> products = productMapper.selectByExample(productExample);

        //获取商品id对应的spec信息
        GoodsSpecExample specExample = new GoodsSpecExample();
        specExample.createCriteria().andGoodsIdEqualTo(id).andDeletedEqualTo(false);
        List<GoodsSpec> specifications = specMapper.selectByExample(specExample);

        return new GoodsDetailVO(attributes, categoryIds, goods, products, specifications);
    }

    /**
     * 获取商品所有的类别及其产商信息
     * 注意不显示deleted为1的数据
     *
     * @return
     */
    @Override
    public GoodsCatAndBrandVO queryCatAndBrand() {
        //先获取全部的产商信息brand
        //只获取id和name，使用自定义的mapper
        GoodsBrandExample brandExample = new GoodsBrandExample();
        brandExample.createCriteria().andDeletedEqualTo(false);
        List<BrandBO> brandList = brandMapper.selectCustom(brandExample);

        List<CategoryVO> categoryList = new ArrayList<>();
        GoodsCategoryExample categoryExample = new GoodsCategoryExample();
        categoryExample.createCriteria().andPidEqualTo(0).andDeletedEqualTo(false);
        List<GoodsCategory> goodsCategories = categoryMapper.selectByExample(categoryExample);
        for (GoodsCategory goodsCategory : goodsCategories) {
            Integer value = goodsCategory.getId();
            String label = goodsCategory.getName();
            List<CatChildrenVO> children = categoryMapper.selectCustom(value);

            CategoryVO categoryVO = new CategoryVO(value, label, children);
            categoryList.add(categoryVO);
        }

        return new GoodsCatAndBrandVO(brandList, categoryList);
    }

    /**
     * 更新商品相关的信息
     * 以及删除某些标签
     */
    @Override
    public int updateGoods(GoodsUpdateBO goodsUpdateBO) {
        //更新updatetime
        Date date = new Date();
        //更新goods表
        Goods goods = goodsUpdateBO.getGoods();
       /* int i = checkGoods(goods);
        if(i == 1){
            return -1;
        }*/
        //System.out.println(goods.getGallery().toString());
        goods.setUpdateTime(date);
        //商品编号与商品id保持一致
        //新的商品id
        Integer id = Integer.parseInt(goods.getGoodsSn());
        //旧的id
        Integer goodsId = goods.getId();
        //先判断 id 和 goodsSn是否相同，若相同，则表示没有修改goodsSn，反之则修改了
        //一定不能用 ！= 比较，因为比较的是地址，永远为true
       /* if(id != goodsId){

            if(isExist(goods.getGoodsSn())){
                return 0;
            }
        }*/
        if (!id.equals(goodsId)) {
            //再判断 修改后的商品编号 是否 存在与数据库中
            //然后再将商品编号与商品id保持一致
            if (isExist(goods.getGoodsSn())) {
                return 0;
            }
        }
        GoodsExample goodsExample = new GoodsExample();
        goodsExample.createCriteria().andIdEqualTo(goodsId);
        Goods g = new Goods(id, goods.getGoodsSn(), goods.getName(), goods.getCategoryId(), goods.getBrandId(), goods.getGallery(),
                goods.getKeywords(), goods.getBrief(), goods.getIsOnSale(), goods.getSortOrder(), goods.getPicUrl(),
                goods.getShareUrl(), goods.getIsNew(), goods.getIsHot(), goods.getUnit(), goods.getCounterPrice(),
                goods.getRetailPrice(), goods.getAddTime(), goods.getUpdateTime(), goods.getDeleted(), goods.getDetail());
        goodsMapper.updateByExampleSelective(g, goodsExample);

        //更新specification表
        List<GoodsSpec> specifications = goodsUpdateBO.getSpecifications();
        GoodsSpecExample specExample = new GoodsSpecExample();
        //若返回的specifications为null， 那么表示标签全部没了
        //需要把对应的spec的deleted全部设置为true
        if (specifications.size() == 0) {
            specExample.createCriteria().andGoodsIdEqualTo(goodsId);
            List<GoodsSpec> specs = specMapper.selectByExample(specExample);
            for (GoodsSpec spec : specs) {
                spec.setDeleted(true);
                specMapper.updateByPrimaryKeySelective(spec);
            }
        }
        //用来获取更新后的specId
        List<Integer> specIds = new ArrayList<>();
        for (GoodsSpec spec : specifications) {
            specIds.add(spec.getId());
            spec.setGoodsId(id);
            specMapper.updateByPrimaryKeySelective(spec);
            if (spec.getAddTime() == null) {
                GoodsSpec goodsSpec = new GoodsSpec(null, id, spec.getSpecification(), spec.getValue(), spec.getPicUrl(),
                        date, date, false);
                specMapper.insertSelective(goodsSpec);
            }
        }
        //删除商品规格标签
        //GoodsSpecExample specExample = new GoodsSpecExample();
        //筛选出goodsId对应的specId不在更新后的SpecId集合中的specId
        specExample.createCriteria().andGoodsIdEqualTo(id).andIdNotIn(specIds);
        //获取其数据（集）
        List<GoodsSpec> goodsSpecs = specMapper.selectByExample(specExample);
        //将其deleted更改为true，别忘了获取attribute的时候要筛选deleted不为true的数据
        for (GoodsSpec goodsSpec : goodsSpecs) {
            goodsSpec.setDeleted(true);
            specMapper.updateByPrimaryKeySelective(goodsSpec);
        }

        //更新attribute表
        List<GoodsAttribute> attributes = goodsUpdateBO.getAttributes();
        List<Integer> attIds = new ArrayList<>();
        GoodsAttributeExample attributeExample = new GoodsAttributeExample();
        //若为空
        if (attributes.size() == 0) {
            attributeExample.createCriteria().andGoodsIdEqualTo(goodsId);
            List<GoodsAttribute> atts = goodsAttributeMapper.selectByExample(attributeExample);
            for (GoodsAttribute att : atts) {
                att.setDeleted(true);
                goodsAttributeMapper.updateByPrimaryKeySelective(att);
            }
        }
        for (GoodsAttribute attribute : attributes) {
            attribute.setGoodsId(id);
            attIds.add(attribute.getId());
            goodsAttributeMapper.updateByPrimaryKeySelective(attribute);
            if (attribute.getAddTime() == null) {
                GoodsAttribute goodsAttribute = new GoodsAttribute(null, id, attribute.getAttribute(), attribute.getValue(), date, date, false);
                goodsAttributeMapper.insertSelective(goodsAttribute);
            }
        }
        //删除商品参数标签
        //GoodsAttributeExample attributeExample = new GoodsAttributeExample();
        //goodsId包含的attributeId不在集合中的话，就表示被删除了
        attributeExample.createCriteria().andGoodsIdEqualTo(id).andIdNotIn(attIds);
        List<GoodsAttribute> attributes1 = goodsAttributeMapper.selectByExample(attributeExample);
        for (GoodsAttribute goodsAttribute : attributes1) {
            goodsAttribute.setDeleted(true);
            goodsAttributeMapper.updateByPrimaryKeySelective(goodsAttribute);
        }

        //更新product表
        List<GoodsProduct> products = goodsUpdateBO.getProducts();
        for (GoodsProduct product : products) {
            product.setGoodsId(id);
            if (product.getId() == 0) {
                GoodsProduct gp = new GoodsProduct(null, product.getGoodsId(), product.getSpecifications(), product.getPrice(),
                        product.getNumber(), product.getUrl(), date, date, false);
                productMapper.insertSelective(gp);
            } else {
                productMapper.updateByPrimaryKeySelective(product);
            }
        }
        return 1;
    }

    /**
     * 增加商品信息及相关信息
     *
     * @param goodsUpdateBO
     */
    @Override
    public int createGoods(GoodsUpdateBO goodsUpdateBO) {
        //判断商品信息是否为null
        Goods goods = goodsUpdateBO.getGoods();
        //int i = checkGoods(goods);
        /*if(i == 1){
            return -1;
        }*/
        //判断id是否已存在
        if (isExist(goods.getGoodsSn())) {
            return 0;
        }
        //id
        goods.setId(Integer.parseInt(goods.getGoodsSn()));
        Integer id = goods.getId();
        //sort_order, 不知道是什么作用
        goods.setSortOrder(null);
        //share_url 为空字符串
        goods.setShareUrl(" ");
        //updatetime & add_time
        Date date = new Date();
        goods.setAddTime(date);
        goods.setUpdateTime(date);
        //deleted
        goods.setDeleted(false);
        //图片功能还未完成,先设置为null
        //goods.setGallery(null);
        int insert = goodsMapper.insertSelective(goods);

        //增加specification信息
        List<GoodsSpec> specifications = goodsUpdateBO.getSpecifications();
        for (GoodsSpec spec : specifications) {
            GoodsSpec gs = new GoodsSpec(null, id, spec.getSpecification(), spec.getValue(), spec.getPicUrl(),
                    date, date, false);
            specMapper.insertSelective(gs);
        }

        //增加products信息
        List<GoodsProduct> products = goodsUpdateBO.getProducts();
        for (GoodsProduct product : products) {
            GoodsProduct gp = new GoodsProduct(null, id, product.getSpecifications(), product.getPrice(),
                    product.getNumber(), product.getUrl(), date, date, false);
            productMapper.insertSelective(gp);
        }

        //增加attribute信息
        List<GoodsAttribute> attributes = goodsUpdateBO.getAttributes();
        for (GoodsAttribute attribute : attributes) {
            GoodsAttribute ga = new GoodsAttribute(null, id, attribute.getAttribute(), attribute.getValue(), date, date, false);
            goodsAttributeMapper.insertSelective(ga);
        }

        return 1;
    }

    /**
     * 删除商品
     *
     * @param goods
     */
    @Override
    public void goodsDelete(Goods goods) {
        goods.setDeleted(true);
        goodsMapper.updateByPrimaryKeySelective(goods);
    }

    //尚政宇
    @Override
    public Map category(Integer id) {
        //id是商品类目的id，首先要根据id查询对应的pid
        Map map = new HashMap();
        Category category = wxCategoryMapper.selectByPrimaryKey(id);
        Integer pid = category.getPid();
        if (pid == 0) pid = id;
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.createCriteria().andPidEqualTo(pid);
        List<Category> brotherCategory = wxCategoryMapper.selectByExample(categoryExample);
        Category parentCategory = wxCategoryMapper.selectByPrimaryKey(pid);
        map.put("brotherCategory", brotherCategory);
        map.put("parentCategory", parentCategory);
        if (brotherCategory.size() != 0) {
            map.put("currentCategory", brotherCategory.get(0));
        }
        return map;
    }

    @Autowired
    SearchHistoryMapper searchHistoryMapper;

    //尚政宇
    @Override
    public Map list(Integer categoryId, Integer page, Integer size, String keyword, String sort, String order, Integer brandId) {
        PageHelper.startPage(page, size);
        //goodsList
        List<Goods> goodsList = goodsMapper.selectGoodsList(categoryId, keyword, sort, order, brandId);
        Map map = new HashMap();
        //count-->goodsList的大小
        if (brandId == null) {
            int count = goodsList.size();
            map.put("count", count);
        }
        if (categoryId != null) {
            //filterCategoryList
            List<GoodsCategory> filterCategoryList = categoryMapper.selectFilterCategoryList(categoryId, keyword, sort, order, brandId);
            map.put("filterCategoryList", filterCategoryList);
        }
        if (brandId != null) {
            //brand
            GoodsBrand brand = brandMapper.selectByPrimaryKey(brandId);
            map.put("brand", brand);
        }
        map.put("goodsList", goodsList);
        //将搜索记录插入到数据库中，如果该关键字已在数据库中存在，则更新时间
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        if (username == null || keyword == null) {
            return map;
        }
        List<SearchHistory> searchHistories = searchHistoryMapper.selectKeywordByUsername(username);
        Date date = new Date();
        outer:
        if (searchHistories != null) {
            for (SearchHistory searchHistory : searchHistories) {
                if (searchHistory.getKeyword().equals(keyword)) {
                    searchHistoryMapper.updateTime(date, userMapper.selectUserIdByUsername(username), keyword);
                    break outer;
                }
            }
            searchHistoryMapper.insert(
                    new SearchHistory(null, userMapper.selectUserIdByUsername(username), keyword, "wx", date, date, false));
        } else {
            searchHistoryMapper.insert(
                    new SearchHistory(null, userMapper.selectUserIdByUsername(username), keyword, "wx", date, date, false));
        }
        return map;
    }

    @Autowired
    GoodsCommentMapper commentMapper;

    @Autowired
    GroupOnRulesMapper groupOnRulesMapper;
    @Autowired
    IssueMapper issueMapper;

    @Autowired
    GoodsProductMapper goodsProductMapper;

    @Autowired
    GoodsSpecMapper goodsSpecMapper;

    @Autowired
    CollectMapper collectMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    FootprintMapper footprintMapper;

    @Override
    public Map detail(Integer goodsId) {
        //attribute
        List<GoodsAttribute> attribute = goodsAttributeMapper.selectByGoodsId(goodsId);
        //info
        Goods goods = goodsMapper.selectByPrimaryKey(goodsId);
        //brand
        GoodsBrand brand = brandMapper.selectByPrimaryKey(goods.getBrandId());
        //comment
        List<Object> commentList = new ArrayList<>();
        GoodsCommentExample comExample = new GoodsCommentExample();
        comExample.setOrderByClause("add_time desc limit 2");
        comExample.createCriteria().andValueIdEqualTo(goodsId);
        List<GoodsComment> goodsCommentListComments = commentMapper.selectByExample(comExample);
        for (GoodsComment comment : goodsCommentListComments) {
            Map wxVO = new HashMap();
            User user = userMapper.selectByPrimaryKey(comment.getUserId());
            wxVO.put("avatar", user.getAvatar());
            wxVO.put("nickname", user.getNickname());
            String format = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss").format(comment.getAddTime());
            wxVO.put("addTime", format);
            wxVO.put("content", comment.getContent());
            wxVO.put("id", comment.getId());
            wxVO.put("picList", comment.getPicUrls());
            commentList.add(wxVO);
        }


        HashMap comment = new HashMap();
        comment.put("count", commentList.size());
        comment.put("data", commentList);
        //groupon
        List<GroupOn> groupon = groupOnRulesMapper.selectByGoodsId(goodsId);

        //issue
        List<Issue> issue = issueMapper.selectByExample(new IssueExample());
        //productList
        List<GoodsProduct> productList = goodsProductMapper.selectByGoodsId(goodsId);
        //shareImage
        String shareImage = "";
        //specification
        ArrayList<GoodsSpec> valueList = (ArrayList<GoodsSpec>) goodsSpecMapper.selectByGoodsId(goodsId);

        List list = new ArrayList();
        HashMap specificationList = new HashMap();
        specificationList.put("name", "规格");
        specificationList.put("valueList", valueList);
        list.add(specificationList);
        //userHasCollect
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
        Integer userId = userMapper.selectUserIdByUsername(username);
        Collect collect = collectMapper.selectByUserIdAndValueId(userId, goodsId);
        Integer userHasCollect = collect == null ? 0 : 1;
        Map map = new HashMap();
        map.put("attribute", attribute);
        map.put("brand", brand);
        map.put("comment", comment);
        map.put("groupon", groupon);
        map.put("info", goods);
        map.put("issue", issue);
        map.put("productList", productList);
        map.put("shareImage", shareImage);
        map.put("specificationList", list);
        map.put("userHasCollect", userHasCollect);

        //添加足迹：如果是相同的商品id，则只更新时间，如果是新商品，则插入一条记录
        if (username != null) {
            FootprintExample footprintExample = new FootprintExample();
            footprintExample.createCriteria().andUserIdEqualTo(userId).andGoodsIdEqualTo(goodsId);
            List<Footprint> footprints = footprintMapper.selectByExample(footprintExample);
            Date date = new Date();
            if (footprints.size() != 0) {
                footprintMapper.updateTime(date, userId, goodsId);
            } else {
                footprintMapper.insert(new Footprint(null, userId, goodsId, date, date, false));
            }
        }
        return map;
    }

    @Override
    public Map related(Integer id) {
        //select detailId
        Goods goods = goodsMapper.selectByPrimaryKey(id);
        GoodsExample goodsExample = new GoodsExample();
        goodsExample.createCriteria().andCategoryIdEqualTo(goods.getCategoryId());
        List<Goods> goodsList = goodsMapper.selectByExample(goodsExample);
        Map map = new HashMap();
        map.put("goodsList", goodsList);
        return map;
    }

    @Override
    public List<Goods> wxselectNewgoods() {
        return goodsMapper.selectNewgoods();
    }

    @Override
    public List<Goods> wxselectHotGoods() {
        return goodsMapper.selectHotGoods();
    }

    @Override
    public List<WXFloorGoods> wxselectCategoryFour() {
        return goodsMapper.selectCategoryFour();
    }

    @Override
    public List<Goods> wxselectByCategoryid(Integer id) {
        return goodsMapper.selectByCategoryid(id);
    }

    @Override
    public Integer selectLastId() {
        return goodsMapper.selectLastId();
    }

    @Override
    public void insertfootprintByUseridAndGoodsid(Integer userId, Integer id) {
        Integer oldId = goodsMapper.selectfootprintByUseridAndGoodsid(userId, id);
        Date date = new Date();
        if (oldId == null || oldId == 0){
            goodsMapper.insertfootprintByUseridAndGoodsid(userId, id, date);
        }else {
            goodsMapper.updatefootprintByUseridAndGoodsid(userId,id,date,oldId);
        }

    }

    /**
     *  GOODSX信息是否完整
     */
    /*private int checkGoods(Goods goods){
        if(goods.getId() == null || goods.getGoodsSn().equals("") || goods.getCounterPrice() == null
            || goods.getRetailPrice() ==null || goods.getIsNew() == null || goods.getIsHot() == null
            || goods.getIsOnSale() == null || goods.getUnit() == null || goods.getCategoryId() == null){
            return 1;
        }
        return 0;
    }*/

    /**
     * 判断修改 or 新增的 商品编号是否已存在
     */
    private boolean isExist(String goodsSn) {
        GoodsExample goodsExample = new GoodsExample();
        goodsExample.createCriteria().andIdEqualTo(Integer.parseInt(goodsSn));
        List<Goods> goods = goodsMapper.selectByExample(goodsExample);
        if (goods.size() == 0) {
            return false;
        }
        return true;
    }

    /**
     * 统计报表之商品统计
     *
     * @return
     */
    @Override
    public StatBaseVO getGoodsStat() {
        StatBaseVO statGoodsVO = new StatBaseVO();
        ArrayList<String> columns = new ArrayList<>();
        columns.add("day");
        columns.add("orders");
        columns.add("products");
        columns.add("amount");
        statGoodsVO.setColumns(columns);

        List<GoodsStat> orderStats = goodsMapper.selectGroupByAddTime();
        statGoodsVO.setRows(orderStats);
        return statGoodsVO;

    }
}
