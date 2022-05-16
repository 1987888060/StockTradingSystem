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
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class StockController {


	@Autowired
	private UserService userService;
	@Autowired
	private UserBuyStockService userBuyStockService;
	@Autowired
	private StockService stockService;
	@Autowired
	private HistoryTradeService historyTradeService;
	@Autowired
	private PickedStockService pickedStockService;
	@Autowired
	private StockSimService  stockSimService;
	@Autowired
	private SellingService sellingService;
	@Autowired
	private BuyingService buyingService;


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
	 * @return 返回购买后的信息
	 */
	@RequestMapping ("/buy_stock")
	public ResultData BuyStock(String username,String stockcode,Integer num,double price) throws IOException {

		if (price - 0D < -0.000001){
			return ResultData.fail("购买失败，价格不对");
		}

		//出售价格
		price = Math.round(price*100)/100;


		// 先把当前账户的余额查询
		User user = userService.info(username);
		Double balance = user.getBalance();
		//得到支持的总价格
		Double total = price*num;

		//钱够
		if (total<=balance){
			user.setBalance(balance - total);
			userService.updateById(user);

			//写入buying表
			Buying buying = new Buying();
			buying.setDate(new Date());
			buying.setStockcode(stockcode);
			buying.setUserid(user.getId());
			buying.setNum(num);
			buying.setPrice(price);

			buyingService.save(buying);

			return ResultData.success("购买成功");
		}else{
			return ResultData.fail("购买失败，余额不足");
		}


	}
	/*public ResultData BuyStock(int id ,String username, int num) {


		Selling selling = sellingService.getById(id);

		if (selling == null){
			return ResultData.fail("购买失败，未查询到相关信息");
		}


		//出售价格
		double price = selling.getPrice();
		price = Math.round(price*100)/100;

		Stock stock = null;
		try {
			stock = stockService.getStockByCode(selling.getStockcode());
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 先把当前账户的余额查询
		User user = userService.info(username);
		Double balance = user.getBalance();
		//得到支持的总价格
		Double total = price*num;

		//钱够
		if (total<=balance){
			user.setBalance(balance - total);
			userService.updateById(user);

			if (num < selling.getNum()){
				selling.setNum(selling.getNum() - num);
			}else{
				sellingService.removeById(id);
			}

			//用户username 购买了该股票

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
			trade.setTime(new Date());

			historyTradeService.storage(trade);
			UserBuyStock userBuyStock =  userBuyStockService.find(username, stock.getCode());
			if(userBuyStock == null){
				userBuyStock = new UserBuyStock();
				userBuyStock.setStockcode(stock.getCode());
				userBuyStock.setUsername(username);
				userBuyStock.setStockname(stock.getName());
				userBuyStock.setNum(num);
				userBuyStockService.insert(userBuyStock);
			}else{
				userBuyStock.setNum(num+userBuyStock.getNum());
				userBuyStockService.updateById(userBuyStock);
			}

			//新股出售
			if (selling.getUserid() == 0){
				return ResultData.success("购买成功");
			}

			//得到出售该股票的用户
			user = userService.getById(selling.getUserid());
			//得到钱
			user.setBalance(user.getBalance() + total);
			userService.updateById(user);
			//用户username 出售了该股票

			//进行记录
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
			//type sell
			trade.setType(0);
			//交易时间
			trade.setTime(new Date());

			historyTradeService.storage(trade);

			return ResultData.success("购买成功");
		}else{
			return ResultData.fail("购买失败，余额不足");
		}

	}*/

	/**
	 * 显示当前用户的股票数量
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

	@RequestMapping ("/sellstock")
	public ResultData SellStock(String username,String stockcode,Integer num,double price) throws IOException {


		//取出已有的数量
		UserBuyStock userBuyStock = userBuyStockService.find(username, stockcode);
		Integer num1 = userBuyStock.getNum();
		System.out.println(num);
		System.out.println(num1);
		System.out.println(price);
		User info = userService.info(username);

		if (price - 0D < -0.000001){
			return ResultData.fail("出售失败，价格不对");
		}


		if(num1.intValue()>num.intValue()){
			//数量减少
			userBuyStock.setNum(num1 - num);
			userBuyStockService.updateById(userBuyStock);
			//写入selling表
			Selling selling = new Selling();
			selling.setDate(new Date());
			selling.setStockcode(stockcode);
			selling.setUserid(info.getId());
			selling.setNum(num);
			selling.setPrice(price);

			sellingService.save(selling);

			return ResultData.success("操作成功",0,0);
		}else if(num1.intValue() == num.intValue()){
			//写入selling表
			Selling selling = new Selling();
			selling.setDate(new Date(System.currentTimeMillis()));
			selling.setStockcode(stockcode);
			selling.setUserid(info.getId());
			selling.setNum(num);
			selling.setPrice(price);

			sellingService.save(selling);
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

	@RequestMapping ("/buyStockinfo")
	public ResultData getBuyStockInfo(String code) throws IOException {
		Map<String,String> map = new HashMap<>();
		//0 - 4 卖一到卖五
		//5 - 9 买一到买五

		//从Selling 表中取出 卖一 - 卖五
		List<Selling> list = sellingService.get(code);
		System.out.println(list);

		for (int i = 0;i<5&&i<list.size();i++){
			Selling selling = list.get(i);
			if (selling == null){
				break;
			}
			map.put("sell"+i+"price",""+selling.getPrice());
			map.put("sell"+i+"num",""+selling.getNum());
		}

		//从Buying 表中取出 买一 - 买五
		List<Buying> list1 = buyingService.get(code);
		System.out.println(list1);

		for (int i = 0;i<5&&i<list1.size();i++){
			Buying buying = list1.get(i);
			if (buying == null){
				break;
			}
			map.put("buy"+i+"price",""+buying.getPrice());
			map.put("buy"+i+"num",""+buying.getNum());
		}

		return ResultData.success("操作成功",1000,map);
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

	@RequestMapping("/sellinglist")
	public ResultData sellinglist(Integer page, Integer limit, String daima,HttpServletRequest request) {
		//获取用用户信息
		User user = (User) request.getSession().getAttribute("user");
		System.out.println(user);
		// 构造后的翻页数量
		Page<Selling> sellingPage = new Page<>(page, limit);
		QueryWrapper<Selling> wrapper = new QueryWrapper<>();
		wrapper.ne("userid",user.getId());
		// 翻页后的记录数
		if (daima != null) {
			wrapper.like("stockcode",daima);
			List<Selling> records = sellingService.page(sellingPage, wrapper).getRecords();
			return ResultData.success(0, sellingService.page(sellingPage, wrapper).getTotal(), records);
		} else {
			List<Selling> records = sellingService.page(sellingPage,wrapper).getRecords();
			System.out.println("数量：" + records.size());
			if (records != null) {
				return ResultData.success(0, sellingService.page(sellingPage,wrapper).getTotal(), records);
			} else {
				return ResultData.fail("数据接口异常，请联系管理员");
			}
		}
	}

	@RequestMapping("/sellinglist1")
	public ResultData sellinglist1(Integer page, Integer limit, String daima) {

		// 构造后的翻页数量
		Page<Selling> sellingPage = new Page<>(page, limit);
		// 翻页后的记录数
		if (daima != null) {
			QueryWrapper<Selling> wrapper = new QueryWrapper<>();
			wrapper.like("stockcode",daima);
			List<Selling> records = sellingService.page(sellingPage, wrapper).getRecords();
			return ResultData.success(0, sellingService.page(sellingPage, wrapper).getTotal(), records);
		} else {
			List<Selling> records = sellingService.page(sellingPage).getRecords();
			System.out.println("数量：" + records.size());
			if (records != null) {
				return ResultData.success(0, sellingService.page(sellingPage).getTotal(), records);
			} else {
				return ResultData.fail("数据接口异常，请联系管理员");
			}
		}
	}

	@RequestMapping("/buyinglist")
	public ResultData buyinglist(Integer page, Integer limit, String daima) {

		// 构造后的翻页数量
		Page<Buying> buyingPage = new Page<>(page, limit);
		// 翻页后的记录数
		if (daima != null) {
			QueryWrapper<Buying> wrapper = new QueryWrapper<>();
			wrapper.like("stockcode",daima);
			List<Buying> records = buyingService.page(buyingPage, wrapper).getRecords();
			return ResultData.success(0, buyingService.page(buyingPage, wrapper).getTotal(), records);
		} else {
			List<Buying> records = buyingService.page(buyingPage).getRecords();
			System.out.println("数量：" + records.size());
			if (records != null) {
				return ResultData.success(0, buyingService.page(buyingPage).getTotal(), records);
			} else {
				return ResultData.fail("数据接口异常，请联系管理员");
			}
		}
	}

	@RequestMapping("/mysellinglist")
	public ResultData mysellinglist(Integer page, Integer limit, String daima,HttpServletRequest request) {
		//获取用用户信息
		User user = (User) request.getSession().getAttribute("user");
		// 构造后的翻页数量
		Page<Selling> sellingPage = new Page<>(page, limit);
		QueryWrapper<Selling> wrapper = new QueryWrapper<>();
		wrapper.eq("userid",user.getId());
		// 翻页后的记录数
		if (daima != null) {
			wrapper.like("stockcode",daima);
			List<Selling> records = sellingService.page(sellingPage, wrapper).getRecords();
			return ResultData.success(0, sellingService.page(sellingPage, wrapper).getTotal(), records);
		} else {
			List<Selling> records = sellingService.page(sellingPage,wrapper).getRecords();
			System.out.println("数量：" + records.size());
			if (records != null) {
				return ResultData.success(0, sellingService.page(sellingPage,wrapper).getTotal(), records);
			} else {
				return ResultData.fail("数据接口异常，请联系管理员");
			}
		}
	}

	@RequestMapping("/mybuyinglist")
	public ResultData mybuyinglist(Integer page, Integer limit, String daima,HttpServletRequest request) {
		//获取用用户信息
		User user = (User) request.getSession().getAttribute("user");
		// 构造后的翻页数量
		Page<Buying> buyingPage = new Page<>(page, limit);
		QueryWrapper<Buying> wrapper = new QueryWrapper<>();
		wrapper.eq("userid",user.getId());
		// 翻页后的记录数
		if (daima != null) {
			wrapper.like("stockcode",daima);
			List<Buying> records = buyingService.page(buyingPage, wrapper).getRecords();
			return ResultData.success(0, buyingService.page(buyingPage, wrapper).getTotal(), records);
		} else {
			List<Buying> records = buyingService.page(buyingPage,wrapper).getRecords();
			System.out.println("数量：" + records.size());
			if (records != null) {
				return ResultData.success(0, buyingService.page(buyingPage,wrapper).getTotal(), records);
			} else {
				return ResultData.fail("数据接口异常，请联系管理员");
			}
		}
	}

	@RequestMapping ("/dissell")
	public ResultData dissell(Integer id) throws IOException {

		Selling byId = sellingService.getById(id);
		if (byId.getUserid() != 0){
			User user = userService.selectById(byId.getUserid());
			UserBuyStock userBuyStock = userBuyStockService.find(user.getUsername(), byId.getStockcode());
			if (userBuyStock == null){
				userBuyStock = new UserBuyStock();
				userBuyStock.setNum(byId.getNum());
				userBuyStock.setStockcode(byId.getStockcode());
				Stock stockByCode = stockService.getStockByCode(byId.getStockcode());
				userBuyStock.setStockname(stockByCode.getName());
				userBuyStock.setUsername(user.getUsername());
				userBuyStockService.insert(userBuyStock);
			}else {
				userBuyStock.setNum(userBuyStock.getNum()+ byId.getNum());
				userBuyStockService.updateById(userBuyStock);
			}

		}

		sellingService.removeById(id);

		return ResultData.success("删除成功",0,0);
	}

	@RequestMapping ("/disbuy")
	public ResultData disbuy(Integer id) throws IOException {

		Buying byId = buyingService.getById(id);

		//将剩余的钱还回去
		//先得到user对象
		User user = userService.selectById(byId.getUserid());
		//计算归还的余额
		double money = byId.getPrice()*byId.getNum();
		//还钱
		user.setBalance(user.getBalance()+money);
		userService.updateById(user);

		buyingService.removeById(id);

		return ResultData.success("删除成功",0,0);
	}

	@RequestMapping ("/dispick")
	public ResultData dispick(Integer id) {

		pickedStockService.deleteByID(id);

		return ResultData.success("删除成功",0,0);
	}

	@RequestMapping ("/addstock")
	public ResultData addstock(StockSim stockSim,int num,double price) {
		//查询股票名是否存在
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.eq("name",stockSim.getName());
		StockSim one = stockSimService.getOne(queryWrapper);
		if (one != null){
			return ResultData.fail("添加失败，股票名已存在");
		}
		queryWrapper.eq("code",stockSim.getCode());
		one = stockSimService.getOne(queryWrapper);
		if (one != null){
			return ResultData.fail("添加失败，股票代码已存在");
		}
		//添加stockSim
		stockSimService.save(stockSim);
		//添加selling
		Selling selling = new Selling();
		selling.setNum(num);
		selling.setDate(new Date());
		selling.setPrice(price);
		selling.setUserid(0);
		selling.setStockcode(stockSim.getCode());
		sellingService.save(selling);
		//添加stock
		Stock stock = new Stock();
		stock.setCode(stockSim.getCode());
		stock.setName(stockSim.getName());
		//默认设置为上海股票
		stock.setType("GP-A");
		String date = ""+(new Date());
		System.out.println(date);
		stock.setDate(date);
		stockService.insert(stock);

		return ResultData.success("添加成功",0,0);
	}

	@RequestMapping ("/updateStockInfo")
	public ResultData updateStockInfo(Stock  stock) {

		//先查询是否存在
		Stock one = null;
		try {
			one = stockService.getStockByCode(stock.getCode());
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (one == null){
			return ResultData.fail("更新失败，未找到对应股票");
		}

		stock.setId(one.getId());
		String date = ""+(new Date());
		stock.setDate(date);
		stockService.update(stock);

		return ResultData.success("更新成功",0,0);
	}

	@RequestMapping ("/addStockNum")
	public ResultData addStockNum(String  code,Integer num,double price) {

		//先查询是否存在
		Stock one = null;
		try {
			one = stockService.getStockByCode(code);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (one == null){
			return ResultData.fail("更新失败，未找到对应股票");
		}

			Selling selling = new Selling();
			selling.setNum(num);
			selling.setStockcode(code);
			selling.setUserid(0);
			selling.setDate(new Date(System.currentTimeMillis()));
			selling.setPrice(price);

			sellingService.save(selling);

		return ResultData.success("更新成功",0,0);
	}

	@RequestMapping ("/delstock")
	public ResultData delstock(String code) {

		//从pickedstock中删除
		pickedStockService.deleteByCode(code);

		//从selling中删除
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.eq("stockcode",code);
		sellingService.remove(queryWrapper);
		//从userBuyStock中删除
		userBuyStockService.remove(queryWrapper);

		//从stock中删除
		stockService.deleteByCode(code);

		//从stockSim中删除
		QueryWrapper queryWrapper1 = new QueryWrapper();
		queryWrapper1.eq("code",code);
		stockSimService.remove(queryWrapper1);

		return ResultData.success("删除成功",0,0);
	}

	@RequestMapping ("/delselling")
	public ResultData delselling(Integer id) throws IOException {

//		//从selling中删除
//		sellingService.removeById(id);
//
//		return ResultData.success("删除成功",0,0);
		return dissell(id);
	}

	@RequestMapping ("/delbuying")
	public ResultData delbuying(Integer id) throws IOException {

		//直接调用disbuy方法
		return disbuy(id);

	}
}
