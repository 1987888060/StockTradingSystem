package com.zxy.controller;

import com.zxy.entity.User;
import com.zxy.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping ("/page")
public class PageController {
	@Autowired
	private UserService userservice;

	/**
	 * 首页
	 *
	 * @return index
	 */
	@RequestMapping ("/index")
	public String index() {
		return "page/index";
	}

	/**
	 * 后台主页
	 *
	 * @return welcome
	 */
	@RequestMapping ("/welcome")
	public String welcome() {
		return "page/welcome";
	}

	@RequestMapping ("/dapan1")
	public String dapan1() {
		return "page/dapan1";
	}

	/**
	 * 个人中心
	 *
	 * @return gereninfo
	 */
	@RequestMapping ("/gereninfo")
	public String gereninfo() {
		return "page/gereninfo";
	}

	/**
	 * 股票列表
	 *
	 * @return stocklist
	 */
	@RequestMapping ("/stocklist")
	public String stocklist() {
		return "page/stocklist";
	}

	/**
	 * 新闻中心
	 *
	 * @return news
	 */
	@RequestMapping ("/news")
	public String news() {
		return "page/news";
	}

	/**
	 * 管理中心界面
	 *
	 * @return forecast
	 */
	@RequestMapping ("/forecast")
	public String forecast(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Object user = session.getAttribute("user");
		System.out.println("登录用户====" + user);
		User userlist = userservice.selectByIds(1);
		System.out.println("管理员用户===="+userlist);
		if (user.equals(userlist)) {
			return "page/forecast";
		} else {
			return "page/stocklist";
		}
	}

	/**
	 * 清除session信息，登录退出
	 *
	 * @param request session作用域
	 * @return 返回到登录页
	 */
	@RequestMapping ("/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		System.out.println(session.getAttribute("user"));
		session.removeAttribute("user");
		return "redirect:/";
	}

	@RequestMapping ("/logout1")
	public String logout1(HttpServletRequest request) {
		HttpSession session = request.getSession();
		System.out.println(session.getAttribute("admin"));
		session.removeAttribute("admin");
		return "redirect:/";
	}

	/**
	 * 个人信息修改
	 *
	 * @return changeinfo
	 */
	@RequestMapping ("/changeinfo")
	public String changeinfo() {
		return "page/changeinfo";
	}


	/**
	 * 持有股票列表
	 *
	 * @return have_stock_list
	 */
	@RequestMapping ("/have_stock_list")
	public String have_stock_list() {
		return "page/have_stock_list";
	}


	/**
	 * 去到新闻详情页面
	 *
	 * @return info
	 */
	@RequestMapping ("/to_info")
	public String to_info() {
		return "page/info";
	}


	@RequestMapping ("/buy_stock")
	public String buyStock() {
		return "page/buy_stock";
	}

	@RequestMapping ("/stockinfo")
	public String stockInfo() {
		return "page/stockinfo";
	}

	@RequestMapping ("/sell_stock")
	public String sellStock() {
		return "page/sell_stock";
	}

	@RequestMapping ("/picked")
	public String pickedStock() {
		return "page/picked";
	}


	@RequestMapping ("/balance")
	public String balance() {
		return "page/balance";
	}

	@RequestMapping ("/historytrade")
	public String historytrade() {
		return "page/historytrade";
	}

	@RequestMapping ("/adminindex")
	public String adminindex() {
		return "page/adminindex";
	}

	@RequestMapping ("/stocklist1")
	public String toStocklist1() {
		return "page/stocklist1";
	}

	@RequestMapping ("/gereninfo1")
	public String toGereninfo1() {
		return "page/gereninfo1";
	}

	@RequestMapping ("/historytrade1")
	public String toHistorytrade1() {
		return "page/historytrade1";
	}


	@RequestMapping ("/changeinfo1")
	public String toChangeinfo1() {
		return "page/changeinfo1";
	}

	@RequestMapping ("/userlist")
	public String userlist() {
		return "page/userlist";
	}

	@RequestMapping ("/selling")
	public String toSelling() {
		return "page/selling";
	}

	@RequestMapping ("/selling1")
	public String toSelling1() {
		return "page/selling1";
	}

	@RequestMapping ("/buying")
	public String toBuying() {
		return "page/buying";
	}

	@RequestMapping ("/myselling")
	public String toMyselling() {
		return "page/myselling";
	}

	@RequestMapping ("/mybuying")
	public String toMybuying() {
		return "page/mybuying";
	}

	@RequestMapping ("/addstock")
	public String toAddstock() {
		return "page/addstock";
	}

	@RequestMapping ("/updateStockInfo")
	public String toUpdateStockInfo() {
		return "page/updateStockInfo";
	}

	@RequestMapping ("/addStockNum")
	public String toAddStockNum() {
		return "page/addStockNum";
	}

}
