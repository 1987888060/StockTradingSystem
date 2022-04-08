package com.zxy.vo;

import com.zxy.entity.Gou;
import lombok.Data;

import java.util.List;
@Data
public class GouVo {
	private Integer current;
	private Integer size;
	private Long total;
	private List<Gou> promptList;
}
