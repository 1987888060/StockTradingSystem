package com.zxy.vo;

import com.zxy.entity.Gupiao;
import lombok.Data;

import java.util.List;

@Data
public class GupiaoVo {
	private Integer current;
	private Integer size;
	private Long total;
	private List<Gupiao> promptList;
}
