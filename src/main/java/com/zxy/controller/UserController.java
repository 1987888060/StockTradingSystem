package com.zxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxy.entity.User;
import com.zxy.service.UserService;
import com.zxy.vo.ResultData;
import com.zxy.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 根据什么选中By
 * 查询select开头
 * 增加save
 * 删除delete
 * 修改update
 * queryWrapper拼接where条件
 * <p>
 * 复杂查询
 * ge,gt,le,lt
 * 大于，大于等于，小于，小于等于
 * eq,ne
 * 等于，不等于
 * between,notBetween
 * 在区间(0,3)之间的值，不在区间的值
 * like,notLike,likeLeft,likeRight
 * 模糊查询包含，不包含，左边包含，右边包含
 * orderBy,orderByDesc,orderByAsc
 * 根据某个字段排序，根据某个字段降序排序，根据某个字段升序排序
 */
@RestController
@RequestMapping ("/user")
public class UserController {
	@Autowired
	UserService userService;

	//"获取所有信息（分页）")
	@GetMapping ("/userPageAll")
	public ResultData selectPageAll(Integer current, Integer size) {
		UserVo userVo = new UserVo();
		IPage<User> page = new Page<>(current, size);
		IPage<User> page1 = userService.page(page, null);
		userVo.setCurrent(current);
		userVo.setSize(size);
		userVo.setTotal(page.getTotal());
		userVo.setPromptList(page.getRecords());
		return ResultData.success(page1);
	}

	//@ApiOperation ("修改列表数据")
	@RequestMapping ("/upd")
	public ResultData upprChoose(@RequestBody User user) {
		boolean update = userService.updateById(user);
		if (update) {
			return ResultData.success(user);
		} else {
			return ResultData.fail("修改失败");
		}
	}

	//@ApiOperation ("添加列表数据")
	@RequestMapping ("/save")
	public ResultData add(@RequestBody User user) {
		boolean save = userService.save(user);
		if (save) {
			return ResultData.success(user);
		} else {
			return ResultData.fail("添加失败");
		}
	}

	//@ApiOperation ("根据id删除列表数据")
	@DeleteMapping ("/deleteById")
	public ResultData delectByid(Integer id) {
		boolean b = userService.removeById(id);
		if (b) {
			return ResultData.success(id);
		} else {
			return ResultData.fail("添加失败");
		}
	}

	//查询所有
	@RequestMapping("/list")
	public ResultData selectList(){
		List<User> list = userService.list();
		return ResultData.success(0,userService.count(),list);
	}

	//休市
	@RequestMapping("/xiu")
	public ResultData upXiu(){
		userService.upxiu();
		return ResultData.success("已休市");
	}
	//开市
	@RequestMapping("/kai")
	public ResultData upKai(){
		userService.upkai();
		return ResultData.success("已开市");
	}
}
