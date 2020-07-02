package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.Address;
import com.cskaoyan.mall.bean.BO.WXAddressSaveBO;
import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.VO.wx.WXAddressDetailVO;
import com.cskaoyan.mall.bean.VO.wx.WXAddressListVO;

import java.util.List;

public interface AddressService {
    BaseData queryAddress(Integer page, Integer limit, String sort, String order, String name, Integer userId);

    List<WXAddressListVO> getAddressListByUsername(String username);

    WXAddressDetailVO selectAddressDetailById(Integer id);

    Integer saveAddress(Address addressBO, String username);

    Integer deleteAddressById(Integer id);
}
