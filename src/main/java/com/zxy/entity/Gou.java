package com.zxy.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName (value = "gou")
public class Gou {
	@TableId (type = IdType.AUTO)
	private Integer id;

	@TableField (value = "userid")
	private String userid;

	@TableField (value = "code")
	private String code;

	@TableField (value = "name")
	private String name;

	@TableField (value = "price")
	private String price;

	@TableField (value = "shuliang")
	private String shuliang;

	@TableLogic
	@TableField (value = "date")
	private String date;
}
