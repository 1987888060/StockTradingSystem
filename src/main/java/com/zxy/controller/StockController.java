package com.zxy.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxy.entity.*;
import com.zxy.service.*;

import com.zxy.vo.ResultData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
public class StockController {

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
			UserBuyStock userBuyStock =  userBuyStockService.find(username, stockcode);
			if(userBuyStock == null){
				userBuyStock = new UserBuyStock();
				userBuyStock.setStockcode(stockcode);
				userBuyStock.setUsername(username);
				userBuyStock.setStockname(stock.getName());
				userBuyStock.setNum(num);
				userBuyStockService.insert(userBuyStock);
			}else{
				userBuyStock.setNum(num+userBuyStock.getNum());
				userBuyStockService.updateById(userBuyStock);
			}


			
			return ResultData.success("购买成功");
		}else{
			return ResultData.fail("购买失败，余额不足");
		}

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
		// 根据当前登录信息的session拿到user
		User user = (User) request.getSession().getAttribute("user");

		System.out.println(user);

		List<UserBuyStock> stocks = userBuyStockService.selectAll(page, limit, user.getUsername()).getList();
		stocks.forEach(System.out::println);
		return ResultData.success(0, stocks.size(), stocks);
	}

	@RequestMapping ("/sell_stock")
	public ResultData SellStock(String username,String stockcode,Integer num) throws IOException {

		//取出已有的数量
		UserBuyStock userBuyStock = userBuyStockService.find(username, stockcode);
		Integer num1 = userBuyStock.getNum();

		Stock stockByCode = stockService.getStockByCode(stockcode);
		String price = stockByCode.getPrice();

		User info = userService.info(username);

		if(num1>num){
			//价格
			double total = num*Double.valueOf(price);
			//钱包加钱
			info.setBalance(info.getBalance()+total);
			userService.updateById(info);
			//数量减少
			userBuyStock.setNum(num1 - num);
			userBuyStockService.updateById(userBuyStock);
			return ResultData.success("出售成功",0,0);
		}else if(num1 == num){
			//价格
			double total = num*Double.valueOf(price);
			//钱包加钱
			info.setBalance(info.getBalance()+total);
			userService.updateById(info);
			//移除
			userBuyStockService.removeById(userBuyStock.getId());
			return ResultData.success("出售成功",0,0);
		}else {
			return ResultData.fail("出售失败，持有的股票数量不够");
		}

	}


	@RequestMapping ("/pick_stock")
	public ResultData pickStock(String username,String code,String stockname) {
		User info = userService.info(username);
		pickedStockService.add(info.getId(),code,stockname);
		return ResultData.success("自选成功");
	}

	@RequestMapping ("/stockinfo")
	public ResultData getStockInfo(String code) throws IOException {
		Stock stockByCode = stockService.getStockByCode(code);
		System.out.println(stockByCode);
		return ResultData.success("操作成功",1000,stockByCode);
	}


	@RequestMapping ("/pikedstock")
	public ResultData pikedStock(HttpServletRequest request, Integer page, Integer limit) {
		// 根据当前登录信息的session拿到user
		User user = (User) request.getSession().getAttribute("user");

		System.out.println(user);

		List<PickedStock> stocks = pickedStockService.selectAll(page,limit,user.getId()).getList();
		stocks.forEach(System.out::println);
		return ResultData.success(0, stocks.size(), stocks);
	}

	@RequestMapping ("/historytrade")
	public ResultData pikedStock( Integer page, Integer limit,String username) {

		if (username != null){
			User info = userService.info(username);
			List<HistoryTrade> stocks = historyTradeService.selectAll(page,limit,info.getId()).getList();
			stocks.forEach(System.out::println);
			return ResultData.success(0, stocks.size(), stocks);
		}else{
			List<HistoryTrade> stocks = historyTradeService.selectAll(page,limit,null).getList();
			stocks.forEach(System.out::println);
			return ResultData.success(0, stocks.size(), stocks);
		}

	}

	@RequestMapping ("/delhistorytrade")
	public ResultData delhistorytrade(Integer id) {

		historyTradeService.remove(id);

		return ResultData.success("删除成功",0,0);


	}
}
