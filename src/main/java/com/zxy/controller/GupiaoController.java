package com.zxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxy.entity.Gupiao;
import com.zxy.entity.User;
import com.zxy.service.GupiaoService;
import com.zxy.vo.GupiaoVo;
import com.zxy.vo.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gupiao")
public class GupiaoController {
	@Autowired
	GupiaoService gupiaoService;

	//"获取所有信息（分页）")
	@GetMapping ("/gupiaoPageAll")
	public ResultData selectPageAll(Integer current, Integer size) {
		GupiaoVo userVo = new GupiaoVo();
		IPage<Gupiao> page = new Page<>(current, size);
		IPage<Gupiao> page1 = gupiaoService.page(page, null);
		userVo.setCurrent(current);
		userVo.setSize(size);
		userVo.setTotal(page.getTotal());
		userVo.setPromptList(page.getRecords());
		return ResultData.success(page1);
	}
	//查询所有
	@RequestMapping("/list")
	public ResultData selectList(Gupiao gupiao){
		List<Gupiao> list = gupiaoService.list(null);
		return ResultData.success(0,list.size(),list);
	}

	//@ApiOperation ("修改列表数据")
	@PutMapping ("/upd")
	public ResultData upprChoose(@RequestBody Gupiao gupiao) {
		boolean update = gupiaoService.updateById(gupiao);
		if (update) {
			return ResultData.success(gupiao);
		} else {
			return ResultData.fail("修改失败");
		}
	}
	//@ApiOperation ("添加列表数据")
	@PostMapping ("/save")
	public ResultData add(@RequestBody Gupiao gupiao) {
		boolean save = gupiaoService.save(gupiao);
		if (save) {
			return ResultData.success(gupiao);
		} else {
			return ResultData.fail("添加失败");
		}
	}

	//@ApiOperation ("根据id删除列表数据")
	@DeleteMapping ("/deleteById")
	public ResultData delectByid(Integer id) {
		boolean b = gupiaoService.removeById(id);
		if (b) {
			return ResultData.success(id);
		} else {
			return ResultData.fail("添加失败");
		}
	}
}
