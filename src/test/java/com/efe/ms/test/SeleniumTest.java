package com.efe.ms.test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumTest {
	
	@Test
	public void chrome() {
		String driverPath = "E:\\softWareInstall\\webDriver\\chromedriver.exe";
//		System.setProperty("webdriver.chrome.driver", chromeDriverPath);
		System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, driverPath);
		WebDriver driver = new ChromeDriver(); // chrome 
		doBusiness(driver);
	}
	
	@Test
	public void firefox() {
		String driverPath = "E:\\softWareInstall\\webDriver\\geckodriver.exe";
		System.setProperty(GeckoDriverService.GECKO_DRIVER_EXE_PROPERTY, driverPath);
		
		WebDriver driver = new FirefoxDriver();
		doBusiness(driver);
	}
	
	@Test
	public void phantomjs() {
		String chromeDriverPath = "E:\\softWareInstall\\webDriver\\phantomjs.exe";
		System.setProperty(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, chromeDriverPath);
		
//		GECKO_DRIVER_EXE_PROPERTY
		WebDriver driver = new PhantomJSDriver();
		doBusiness(driver);
	}
	
	private void doBusiness(WebDriver driver) {
		driver.get("http://hengzhiyi.cn/company_news.html");
//		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#companyNews > tr")));
		
		System.out.println(driver.getTitle());
		System.out.println(driver.getPageSource());
		System.out.println("--------------------------华丽的分割线--------------------------------"); 
		List<WebElement> trs = driver.findElements(By.cssSelector("#companyNews > tr"));
		Optional.ofNullable(trs).orElse(Collections.emptyList()).forEach( ele -> {
			WebElement e = ele.findElement(By.cssSelector("ul > li:first-child > a"));
			System.out.println(e == null ? "null" : e.getText());
		});
		
		driver.quit();
	}

}
