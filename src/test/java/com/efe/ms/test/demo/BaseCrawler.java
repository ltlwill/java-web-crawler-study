package com.efe.ms.test.demo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;

public class BaseCrawler {
	
	protected WebDriver chromeDriver() {
		String driverPath = "E:\\softWareInstall\\webDriver\\chromedriver.exe";
//		System.setProperty("webdriver.chrome.driver", chromeDriverPath);
		System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, driverPath);
		return new ChromeDriver(); // chrome 
	}
	
	protected WebDriver chromeHeadlessDriver() {
		String driverPath = "E:\\softWareInstall\\webDriver\\chromedriver.exe";
		System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, driverPath);
		ChromeOptions opts = new ChromeOptions();
//		opts.addArguments("--headless");
		opts.setHeadless(true); 
		return new ChromeDriver(opts); // chrome --headless模式（无界面模式）
	}
	
	protected WebDriver firefoxDriver() {
		String driverPath = "E:\\softWareInstall\\webDriver\\geckodriver.exe";
		System.setProperty(GeckoDriverService.GECKO_DRIVER_EXE_PROPERTY, driverPath);
		return new FirefoxDriver();
	}
	
	protected WebDriver phantomjsDriver() {
		String driverPath = "E:\\softWareInstall\\webDriver\\phantomjs.exe";
		System.setProperty(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, driverPath);
		return new PhantomJSDriver();
	}

}
