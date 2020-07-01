package com.cskaoyan.mall.bean.wx;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HandleOption {
    boolean cancel;
    boolean comment;
    boolean confirm;
    boolean delete;
    boolean pay;
    boolean rebuy;
    boolean refund;
}
