package com.efe.ms.test.demo;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebWindow;
import com.gargoylesoftware.htmlunit.html.DomAttr;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * 使用htmlunit 框架爬取gitee.com信息
 * @author Tianlong Liu
 * @2019年12月27日 下午5:03:02
 */
public class GiteeHtmlUnitCrawler extends BaseCrawler{
	
	@Test
	public void test1() {
		WebClient client = null;
		try {
			client = htmlUnitWebClient();
			// 登录
			login(client);
			Thread.sleep(5000);
			// 解析目标页面
			parsePage(client);
		}catch (Exception e) {
			logger.info(e.getMessage()); 
		}finally {
			if(client != null) {
				client.close();
			}
		}
	}
	
	private void login(WebClient client) throws Exception{
		String loginPageUrl = "https://gitee.com/login";
		HtmlPage loginPage = client.getPage(loginPageUrl);
		client.waitForBackgroundJavaScript(1000 * 15);
		
//		DomElement ele = loginPage.querySelector("#new_user #user_login"); // 用户名
//		ele.setNodeValue("x@qq.com");
//		ele = loginPage.querySelector("#new_user #user_password"); // 密码
//		ele.setNodeValue("xx");
//		ele = loginPage.querySelector("#new_user input[name=\"commit\"]"); // 提交表单按钮
//		return ele.click(); // 点击登录
		
		HtmlInput name = loginPage.getFirstByXPath("//input[@id='user_login']");
		name.setValueAttribute(LOGIN_USER_NAME); // 用户名
		HtmlInput pwd = loginPage.getFirstByXPath("//input[@id='user_password']");
		pwd.setValueAttribute(LOGIN_USER_PWD); // 密码
		HtmlInput btn = loginPage.getFirstByXPath("//input[@name='commit']");
		btn.click();
	}
	
	private void parsePage(WebClient client) throws Exception{
		String pageUrl = "https://gitee.com/iiteemo/dashboard";
		HtmlPage page = client.getPage(pageUrl);
		client.waitForBackgroundJavaScript(1000 * 15);
		DomNodeList<DomNode> nodes = page.querySelectorAll("#recommend-project__container .items .item");
		nodes.forEach(node -> {
			DomNode title = node.querySelector(".recommend-list .recommend-project__name a");
			DomNode subTitle = node.querySelector(".recommend-list .recommend-project__describe");
			System.out.println(node.getIndex() + ":" + title.getTextContent() + " | " 
					+ subTitle.getTextContent() + " | " 
					+ ((DomAttr)title.getAttributes().getNamedItem("href")).getValue()); 
		});
	}
}
