package com.cskaoyan.mall.utils;

import lombok.experimental.UtilityClass;

/**
 * @author 韩
 * @create 2020-07-03 10:01
 */
@UtilityClass
public class CharacterArrayConversionUtils {
    public String[] stringConvertsAnArrayOfStrings(String string){
        //取出的数据是形如 "[1]" 、 "[2]" 、 "[1,2]"
        //对取出的数据做处理
        string = string.replace("[", "");
        string = string.replace("]", "");
        string = string.replace(" ", "");
        return string.split(",");
    }
}
