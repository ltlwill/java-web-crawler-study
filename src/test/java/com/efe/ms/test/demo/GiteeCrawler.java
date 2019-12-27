package com.efe.ms.test.demo;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * 爬取 gitee.com 的信息
 * @author Tianlong Liu
 * @2019年12月26日 下午3:06:42
 */
public class GiteeCrawler extends BaseCrawler{
	
	@Test
	public void test1() {
//		WebDriver driver = chromeDriver();  // 成功
//		WebDriver driver = chromeHeadlessDriver(); // 失败，非常慢
//		WebDriver driver = firefoxDriver(); // 成功
		WebDriver driver = phantomjsDriver(); // 成功
		try {
			// login 
			doLogin(driver);
			// parse page
			parseTargetPage(driver);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			driver.close();
		}
	}
	
	private void parseTargetPage(WebDriver driver) throws Exception{
		// 获取目标页面
		String url = "https://gitee.com/iiteemo/dashboard";
		driver.get(url);
//		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#recommend-project__container .recommend-projects"))); 
		String page = driver.getPageSource();
		System.out.println(page);
		
		List<WebElement> eles = driver.findElements(By.cssSelector("#recommend-project__container .items .item"));
		Optional.ofNullable(eles).orElse(Collections.emptyList()).forEach(ele -> {
			WebElement title = ele.findElement(By.cssSelector(".recommend-list .recommend-project__name a"));
			WebElement subTitle = ele.findElement(By.cssSelector(".recommend-list .recommend-project__describe"));
			System.out.println(title.getText() + " | " + subTitle.getText() + " | " + title.getAttribute("href"));
		});
		
		// jsoup
		Document doc = Jsoup.parse(page);
		Elements els = doc.select("#recommend-project__container .items .item");
		els.forEach(e -> {
			Element title = e.selectFirst(".recommend-list .recommend-project__name a"); 
			Element subTitle = e.selectFirst(".recommend-list .recommend-project__describe");
			System.out.println(title.text() + " | " + subTitle.text() + " | " + title.attr("href"));
		});
	}
	
	private void doLogin(WebDriver driver) {
		String loginUrl = "https://gitee.com/login";
		driver.get(loginUrl);
		// 静茹登录页面
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#new_user #user_login")));
		
		// 执行登录操作
		WebElement nameEle = driver.findElement(By.cssSelector("#new_user #user_login"));
		nameEle.sendKeys("xx@qq.com");
		WebElement pwdEle = driver.findElement(By.cssSelector("#new_user #user_password"));
		pwdEle.sendKeys("txx");
		WebElement submitEle = driver.findElement(By.cssSelector("#new_user input[name=\"commit\"]"));
		submitEle.click();
		
		// 登录成功，并且跳转到主页
		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".header-menu #my-gitee-dropdown")));
	}

}
