package com.hohai.pojo.vo;

import lombok.Data;

@Data
public class PortalVo {

    private String keyWords;  // 搜索标题关键字

    private Integer type;    // 新闻类型

    private Integer pageNum;  // 页码数

    private Integer pageSize; // 页大小
}
