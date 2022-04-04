package com.zxy.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName (value = "gupiao")
public class Gupiao {
	@TableId
	private Integer id;

	private String daima;

	private String mingcheng;

	private String zuoshou;

	private String shiyinglv;

	private String price;

	//    @TableField(exist = false)
	private String head;

	private String date;
}
