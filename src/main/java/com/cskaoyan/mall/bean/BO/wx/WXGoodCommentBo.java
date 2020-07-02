package com.cskaoyan.mall.bean.BO.wx;

/* * 
@author  Walker-胡
@create 2020-07-01 21:22
*/

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WXGoodCommentBo {
   private      Byte type;
   private      Integer size;
   private      Integer page;
   private      Byte showType;
   private      Integer valuedId;
}
