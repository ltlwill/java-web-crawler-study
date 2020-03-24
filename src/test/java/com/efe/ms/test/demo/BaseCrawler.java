package com.efe.ms.test.demo;

import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;

public class BaseCrawler {
	
	protected Logger logger = Logger.getLogger(BaseCrawler.class.getName());
	
	protected static final String LOGIN_USER_NAME = "389261468@qq.com";
	protected static final String LOGIN_USER_PWD = "teemo.666";
	
	/**
	 * chrome 浏览器驱动
	 * @return
	 */
	protected WebDriver chromeDriver() {
		String driverPath = "E:\\softWareInstall\\webDriver\\chromedriver.exe";
//		System.setProperty("webdriver.chrome.driver", chromeDriverPath);
		System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, driverPath);
		return new ChromeDriver(); // chrome 
	}
	
	/**
	 * chrome headless模式驱动
	 * @return
	 */
	protected WebDriver chromeHeadlessDriver() {
		String driverPath = "E:\\softWareInstall\\webDriver\\chromedriver.exe";
		System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, driverPath);
		ChromeOptions opts = new ChromeOptions();
//		opts.addArguments("--headless");
		opts.setHeadless(true); 
		return new ChromeDriver(opts); // chrome --headless模式（无界面模式）
	}
	
	/**
	 * firefox浏览器驱动
	 * @return
	 */
	protected WebDriver firefoxDriver() {
		String driverPath = "E:\\softWareInstall\\webDriver\\geckodriver.exe";
		System.setProperty(GeckoDriverService.GECKO_DRIVER_EXE_PROPERTY, driverPath);
		return new FirefoxDriver();
	}
	
	/**
	 * phantomjs 无界面浏览器驱动
	 * @return
	 */
	protected WebDriver phantomjsDriver() {
		String driverPath = "E:\\softWareInstall\\webDriver\\phantomjs.exe";
		System.setProperty(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, driverPath);
		return new PhantomJSDriver();
	}
	
	/**
	 * 创建htmlunit WebClient
	 * @return
	 */
	protected WebClient htmlUnitWebClient() {
		WebClient webClient = new WebClient(BrowserVersion.CHROME);
		webClient.getOptions().setCssEnabled(false); // 无界面，不需要开启CSS
		webClient.getOptions().setDownloadImages(false); // 不需要加载图片
		webClient.getOptions().setJavaScriptEnabled(true); // 有ajax请求，需要开启
		webClient.getOptions().setActiveXNative(false);
		webClient.getOptions().setThrowExceptionOnScriptError(false); // 当JS执行出错的时候是否抛出异常, 这里选择不需要
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false); // 当HTTP的状态非200时是否抛出异常, 这里选择不需要
		webClient.setAjaxController(new NicelyResynchronizingAjaxController()); // 设置支持AJAX
		return webClient;
	}

}
