package com.zxy.vo;

import com.zxy.entity.User;
import lombok.Data;

import java.util.List;
@Data
public class UserVo {
	private Integer current;
	private Integer size;
	private Long total;
	private List<User> promptList;
}
