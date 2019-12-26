package com.efe.ms.test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * 使用 Selenium 获取动态网页数据（分页）
 * 
 * @author Tianlong Liu
 * @2019年12月19日 下午5:24:23
 */
public class SeleniumTest {
	
	/**
	 * 使用chrome 浏览器   有界面
	 */
	@Test
	public void chrome() {
		String driverPath = "E:\\softWareInstall\\webDriver\\chromedriver.exe";
//		System.setProperty("webdriver.chrome.driver", chromeDriverPath);
		System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, driverPath);
		WebDriver driver = new ChromeDriver(); // chrome 
		doBusiness(driver);
	}
	
	/**
	 * 使用 chrome 浏览器 无界面 --headless （注：速度很慢）
	 */
	@Test
	public void chromeHeadless() {
		String driverPath = "E:\\softWareInstall\\webDriver\\chromedriver.exe";
		System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, driverPath);
		ChromeOptions opts = new ChromeOptions();
//		opts.addArguments("--headless");
		opts.setHeadless(true); 
		WebDriver driver = new ChromeDriver(opts); // chrome --headless模式（无界面模式）
		doBusiness(driver);
	}
	
	/**
	 * 使用firefox 浏览器
	 */
	@Test
	public void firefox() {
		String driverPath = "E:\\softWareInstall\\webDriver\\geckodriver.exe";
		System.setProperty(GeckoDriverService.GECKO_DRIVER_EXE_PROPERTY, driverPath);
		
		WebDriver driver = new FirefoxDriver();
		doBusiness(driver);
	}
	
	/**
	 * 使用 phantomjs 无界面
	 */
	@Test
	public void phantomjs() {
		String driverPath = "E:\\softWareInstall\\webDriver\\phantomjs.exe";
		System.setProperty(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, driverPath);
		
//		GECKO_DRIVER_EXE_PROPERTY
		WebDriver driver = new PhantomJSDriver();
		doBusiness(driver);
	}
	
	private void doBusiness(WebDriver driver) {
		driver.get("http://hengzhiyi.cn/company_news.html");
//		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#companyNews tr")));
		
		System.out.println(driver.getTitle());
		System.out.println(driver.getPageSource());
		System.out.println("--------------------------华丽的分割线--------------------------------"); 
		// 获取当前页面所有的新闻标题
		printNewsTitle(driver); // 第一页的
		
		// 如果有下一页
		
		System.out.println("-----------------处理完成------------------");
		driver.quit();
	}
	
	private void printNewsTitle(WebDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, 15);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#companyNews tr")));
		// 获取当前页面所有的新闻标题
		List<WebElement> trs = driver.findElements(By.cssSelector("#companyNews tr"));
		Optional.ofNullable(trs).orElse(Collections.emptyList()).forEach( ele -> {
			WebElement e = ele.findElement(By.cssSelector("ul > li:first-child > a"));
			System.out.println(e == null ? "null" : e.getText());
		});
		JavascriptExecutor jsExecutor = ((JavascriptExecutor) driver);
        // 清空列表元素，以便于后面点击下一页按钮时判断ajax请求是否完成
		jsExecutor.executeScript("document.querySelector('#companyNews').innerHTML = ''");
		// 下一页按钮
		WebElement nextBtn = driver.findElement(By.cssSelector(".text-center > a.btn_next"));
		String classText = nextBtn.getAttribute("class");
		// 如果没有下一页则不处理
		if(classText == null || "".equals(classText.trim()) || classText.contains("hide")) {
			return;
		}
		nextBtn.click(); // 点击下一页按钮
		System.out.println("-------------请求下一页数据-------------");
		printNewsTitle(driver); 
	}

}
