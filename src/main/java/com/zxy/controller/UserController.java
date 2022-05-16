package com.zxy.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxy.entity.Selling;
import com.zxy.entity.StockSim;
import com.zxy.entity.User;
import com.zxy.service.PickedStockService;
import com.zxy.service.SellingService;
import com.zxy.service.UserBuyStockService;
import com.zxy.service.UserService;
import com.zxy.vo.ResultData;
import com.zxy.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
	@Autowired
	UserBuyStockService userBuyStockService;
	@Autowired
	PickedStockService pickedStockService;
	@Autowired
	SellingService sellingService;

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
	@RequestMapping ("/deleteById")
	public ResultData delectByid(Integer id) {

		User user = userService.selectById(id);
		if (user == null){
			return ResultData.fail("删除失败，未找到");
		}
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.eq("username",user.getUsername());
		userBuyStockService.remove(queryWrapper);


		QueryWrapper queryWrapper1 = new QueryWrapper();
		queryWrapper1.eq("userid",user.getId());

		sellingService.remove(queryWrapper1);

		pickedStockService.deleteByUserid(user.getId());

		boolean b = userService.removeById(id);




		if (b) {
			return ResultData.success("删除成功",0,0);
		} else {
			return ResultData.fail("删除失败");
		}
	}

	//查询所有
	@RequestMapping("/list")
	public ResultData selectList(Integer page, Integer limit, String username){
		Page<User> userPage = new Page<>(page, limit);

		System.out.println(username);
		if (username != null) {
			QueryWrapper<User> wrapper = new QueryWrapper<>();
			wrapper.like("username",username);
			List<User> records = userService.page(userPage, wrapper).getRecords();
			return ResultData.success(0, userService.page(userPage, wrapper).getTotal(), records);
		} else {
			List<User> records = userService.page(userPage).getRecords();
			System.out.println("数量：" + records.size());
			if (records != null) {
				return ResultData.success(0, userService.page(userPage).getTotal(), records);
			} else {
				return ResultData.fail("数据接口异常，请联系管理员");
			}
		}
	}


	//充值
	@RequestMapping("/recharge")
	public ResultData recharge(double money, HttpServletRequest request){
		User user = (User) request.getSession().getAttribute("user");

		user = userService.info(user.getUsername());

		user.setBalance(user.getBalance() + money);

		userService.updateById(user);

		return ResultData.success("充值成功",0,null);
	}

	//提现
	@RequestMapping("/withdrawal")
	public ResultData withdrawal(double money, HttpServletRequest request){
		User user = (User) request.getSession().getAttribute("user");

		user = userService.info(user.getUsername());
		if ((user.getBalance() - money)>=0){
			user.setBalance(user.getBalance() - money);
			userService.updateById(user);
			return ResultData.success("提现成功",0,null);
		}else {
			return ResultData.fail("余额不足");
		}

	}

}
