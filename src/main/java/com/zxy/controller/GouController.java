package com.zxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxy.entity.Gou;
import com.zxy.entity.User;
import com.zxy.service.GouService;
import com.zxy.service.UserService;
import com.zxy.vo.GouVo;
import com.zxy.vo.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/gou")
public class GouController {
	@Autowired
	GouService gouService;

	@Autowired
	UserService userService;
	//"获取所有信息（分页）")
	@GetMapping ("/gupiaoPageAll")
	public ResultData selectPageAll(Integer current, Integer size) {
		GouVo userVo = new GouVo();
		IPage<Gou> page = new Page<>(current, size);
		IPage<Gou> page1 = gouService.page(page, null);
		userVo.setCurrent(current);
		userVo.setSize(size);
		userVo.setTotal(page.getTotal());
		userVo.setPromptList(page.getRecords());
		return ResultData.success(page1);
	}

	//@ApiOperation ("修改列表数据")
	@PutMapping ("/upd")
	public ResultData upprChoose(@RequestBody Gou gou) {
		boolean update = gouService.updateById(gou);
		if (update) {
			return ResultData.success(gou);
		} else {
			return ResultData.fail("修改失败");
		}
	}
	//@ApiOperation ("添加列表数据")
	@PostMapping ("/save")
	public ResultData add(@RequestBody Gou gou) {
		boolean save = gouService.save(gou);
		if (save) {
			return ResultData.success(gou);
		} else {
			return ResultData.fail("添加失败");
		}
	}

	//@ApiOperation ("根据id删除列表数据")
	@DeleteMapping ("/deleteById")
	public ResultData delectByid(Integer id) {
		boolean b = gouService.removeById(id);
		if (b) {
			return ResultData.success(id);
		} else {
			return ResultData.fail("添加失败");
		}
	}

	//购买股票数量
	@PutMapping("add")
	public ResultData addShuliang(@RequestBody Gou gou){
		//前端点击购买，获取用户id
		String userid = gou.getUserid();
		Integer i = null;
		if(userid!=null){
			i = Integer.valueOf(userid);
		}
		//根据用户id查询信息
		User user = userService.selectByIds(i);
		//判断购买权限字段是否为1,是，
		if (user.getStock_nums()==1) {
			//修改数量
			gouService.updateById(gou);
			return ResultData.success("购买成功");
		} else {
			return ResultData.fail("购买失败");
		}
	}
}
