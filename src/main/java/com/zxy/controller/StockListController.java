package com.zxy.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxy.entity.*;
import com.zxy.service.*;

import com.zxy.vo.ResultData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
public class StockListController {

	@Autowired
	private StockListService stockListService;

	@Autowired
	private UserService userService;

	@Autowired
	private UserBuyStockService userBuyStockService;

	@Autowired
	private UserHaveStocksService userHaveStocksService;

	@Autowired
	private GupiaoService gupiaoService;

	@Autowired
	private StockService stockService;

	@Autowired
	private HistoryTradeService historyTradeService;

	@Autowired
	private PickedStockService pickedStockService;

	@Autowired
	private StockSimService  stockSimService;
	// @RequestMapping ("/stock_list")
	// public ResultData stock_lists(Integer page, Integer limit) {
	// 	System.out.println("page = " + page + ", limit = " + limit);
	// 	// 构造后的翻页数量
	// 	Page<Stock_List> stock_listPage = new Page<>(page, limit);
	// 	// 翻页后的记录数
	// 	List<Stock_List> records = stockListService.page(stock_listPage).getRecords();
	// 	System.out.println("数量：" + records.size());
	// 	if (records != null) {
	// 		// 将翻页的数量给count
    //         /*long count = service.page(stock_listPage).getTotal();
    //         System.out.println("每一页数量："+count);*/
	// 		return ResultData.success(0, stockListService.page(stock_listPage).getTotal(), records);
	// 	} else {
	// 		return ResultData.fail("数据接口异常，请联系管理员");
	// 	}
	// }
//	@RequestMapping("/stock_list")
//	public ResultData stock_lists(Integer page, Integer limit, String daima) {
//		// 构造后的翻页数量
//		Page<Stock_List> stock_listPage = new Page<>(page, limit);
//		// 翻页后的记录数
//		if (daima != null) {
//			QueryWrapper<Stock_List> wrapper = new QueryWrapper<>();
//			wrapper.eq("daima", daima);
//			List<Stock_List> records = stockListService.page(stock_listPage, wrapper).getRecords();
//			return ResultData.success(0, stockListService.page(stock_listPage, wrapper).getTotal(), records);
//		} else {
//			List<Stock_List> records = stockListService.page(stock_listPage).getRecords();
//			System.out.println("数量：" + records.size());
//			if (records != null) {
//				return ResultData.success(0, stockListService.page(stock_listPage).getTotal(), records);
//			} else {
//				return ResultData.fail("数据接口异常，请联系管理员");
//			}
//		}
//	}

	@RequestMapping("/stock_list")
	public ResultData stock_lists(Integer page, Integer limit, String daima) {
		// 构造后的翻页数量
		Page<StockSim> stockSimPage = new Page<>(page, limit);
		// 翻页后的记录数
		if (daima != null) {
			QueryWrapper<StockSim> wrapper = new QueryWrapper<>();
			wrapper.like("code",daima);
			List<StockSim> records = stockSimService.page(stockSimPage, wrapper).getRecords();
			return ResultData.success(0, stockSimService.page(stockSimPage, wrapper).getTotal(), records);
		} else {
			List<StockSim> records = stockSimService.page(stockSimPage).getRecords();
			System.out.println("数量：" + records.size());
			if (records != null) {
				return ResultData.success(0, stockSimService.page(stockSimPage).getTotal(), records);
			} else {
				return ResultData.fail("数据接口异常，请联系管理员");
			}
		}
	}




	/**
	 * 买股票
	 *
	 * @return 返回购买后的信息
	 */
	@RequestMapping ("/buy_stock")
	public ResultData BuyStock(String stockcode ,String username, int num, HttpServletRequest request) {

		HashMap<String, Object> map = new HashMap<>();
		// 先把当前账户的余额查询
		User user = userService.info(username);
		Double balance = user.getBalance();
		//根据股票代码获取股票信息
		Stock stock = null;
		try {
			stock = stockService.getStockByCode(stockcode);
		} catch (IOException e) {
			e.printStackTrace();
			return ResultData.fail("购买失败,系统错误");
		}

		Double price = Double.valueOf(stock.getPrice());
		Double total = price*num;

		if (total<=balance){
			user.setBalance(balance - total);
			userService.updateById(user);

			UserBuyStock userBuyStock = new UserBuyStock();
			userBuyStock.setStockcode(stockcode);
			userBuyStock.setUsername(username);
			userBuyStock.setNum(num);


			//进行记录
			HistoryTrade trade = new HistoryTrade();
			//股票代码
			trade.setStockcode(stock.getCode());
			//股票名
			trade.setStockname(stock.getName());
			//交易数量
			trade.setNum(num);
			//交易额
			trade.setVolume(total);
			//交易单价
			trade.setPrice(price);
			//who
			trade.setUserid(user.getId());
			//type buy
			trade.setType(1);
			//交易时间
			trade.setTime(new Date(System.currentTimeMillis()));

			historyTradeService.storage(trade);


			userBuyStockService.insert(userBuyStock);

			return ResultData.success("购买成功");
		}else{
			return ResultData.fail("购买失败，余额不足");
		}


//		// 重新请求数据库，拿到更新后的金额数据进行比较
//		User info = userService.info(admin.getUsername());
//		// 购买后股票数+1
//		if (info.getBalance() > aFloat) {
//			System.out.println(admin.getBalance());
//			System.out.println(aFloat);
//			map.put("status", true);
//			map.put("nums", 1);
//			// 购买成功后，让当前的用户金额减少，同时在当前的用户持有列表里面添加当前的股票购买数据
//			info.setBalance((int) (info.getBalance() - aFloat));
//			// 金额更新
//			userService.update(info);
//			userBuyStockService.insert(new UserBuyStock(info.getId(), stocks.getId()));
//			return ResultData.success(map);
//		} else {
//
//		}
	}

	/**
	 * 显示当前用户的股票数量
	 *
	 * @param request session数据
	 * @param page    当前页
	 * @param limit   数据量
	 * @return json数据
	 */
	@RequestMapping ("/have_stock_list")
	public ResultData HaveStockList(HttpServletRequest request, Integer page, Integer limit) {
		// 根据当前登录信息的session拿到id
		User user = (User) request.getSession().getAttribute("user");
		// 根据id查询出当前的账户一共买了好多的股票
		System.out.println(user);
		List<Stocks> stocks = userHaveStocksService.selectAll(page, limit, user.getId()).getList();
		stocks.forEach(System.out::println);
		return ResultData.success(0, stocks.size(), stocks);
	}


	@RequestMapping ("/sell_stock")
	public ResultData SellStock(@RequestBody Stocks stocks) {
		System.out.println(stocks);
		// 根据当前获取到的股票对象删除掉数据库中的信息
		int i = userHaveStocksService.sellStock(stocks.getId());
		return ResultData.success(i);
	}



	//@ApiOperation ("修改列表数据")
	// @PutMapping ("/upd")
	@RequestMapping ("/stock/upd")
	public ResultData upprChoose(@RequestBody Stock_List stock) {
		// User list = userService.selectById(1);
		// int jiaoyi = list.getStock_nums();
		// System.out.println(jiaoyi);
		// if (jiaoyi != 0) {
		// 	ResultData.fail("购买失败");
		// }
		//创建一个当前时间放进实体类
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = formatter.format(date);
		stock.setDate(format);
		//同步数据添加到交易中心表
		Gupiao gupiao = new Gupiao();
		gupiao.setId(stock.getId());
		gupiao.setDaima(stock.getDaima());
		gupiao.setZuoshou(stock.getZuoshou());
		gupiao.setMingcheng(stock.getMingcheng());
		gupiao.setShiyinglv(stock.getShiyinglv());
		gupiao.setPrice(stock.getPrice());
		gupiao.setHead(stock.getHead());
		gupiao.setDate(stock.getDate());
		gupiaoService.save(gupiao);

		boolean update = stockListService.updateById(stock);
		//判断休市否，和购买方法调用成功
		if (update) {
			return ResultData.success(stock);
		} else {
			return ResultData.fail("购买失败");
		}
	}

	@RequestMapping ("/pick_stock")
	public ResultData pickStock(String username,String code) {
		User info = userService.info(username);
		pickedStockService.add(info.getId(),code);
		return ResultData.success("自选成功");
	}

}
