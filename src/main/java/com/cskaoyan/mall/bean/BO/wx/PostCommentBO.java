package com.cskaoyan.mall.bean.BO.wx;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostCommentBO {
    private String content;

    private Boolean hasPicture;

    private String[] picUrls;

    private Short star;

    private Byte type;

    private Integer valuedId;

}
