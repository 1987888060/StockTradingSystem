package com.zxy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("pickedstock")
public class PickedStock {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private int userid;
    private String code;
}
