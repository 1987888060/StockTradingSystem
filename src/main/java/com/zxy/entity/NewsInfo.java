package com.zxy.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "newsinfo")
public class NewsInfo {

    @TableId
    private Integer id;

    private String title;

    private String img_addr;

    private String note;

    private String article_info;
}
