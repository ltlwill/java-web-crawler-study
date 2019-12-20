package com.efe.ms.test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * 使用 HtmlUnit 获取动态网页数据（分页）
 * 
 * @author Tianlong Liu
 * @2019年12月19日 下午5:24:23
 */
public class HtmlUnitTest {

	@Test
	public void test01() throws Exception {
		String pageUrl = "http://hengzhiyi.cn/company_news.html";
		WebClient webClient = null;
		try {
			webClient = new WebClient(BrowserVersion.CHROME);
			webClient.getOptions().setCssEnabled(false); // 无界面，不需要开启CSS
			webClient.getOptions().setJavaScriptEnabled(true); // 有ajax请求，需要开启
			webClient.getOptions().setActiveXNative(false);
			webClient.getOptions().setThrowExceptionOnScriptError(false); // 当JS执行出错的时候是否抛出异常, 这里选择不需要
			webClient.getOptions().setThrowExceptionOnFailingStatusCode(false); // 当HTTP的状态非200时是否抛出异常, 这里选择不需要
			webClient.setAjaxController(new NicelyResynchronizingAjaxController()); // 设置支持AJAX

			HtmlPage page = webClient.getPage(pageUrl);
			webClient.waitForBackgroundJavaScript(1000 * 30); // 30 s

			System.out.println(page.getTitleText());
			System.out.println(page.asXml());

			printNewsTitle(webClient, page);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (webClient != null) {
				webClient.close();
			}
		}
	}

	private void printNewsTitle(WebClient webClient, HtmlPage page) throws Exception {
		Document doc = Jsoup.parse(page.asXml());
		List<Element> eles = doc.select("#companyNews tr");
		Optional.ofNullable(eles).orElse(Collections.emptyList()).forEach(ele -> {
			Element a = ele.selectFirst("ul > li:first-child > a");
			System.out.println(a.text());
		});
//		page.executeJavaScript("document.querySelector('#companyNews').innerHTML = ''");
		doc.selectFirst("#companyNews").empty();

//		Element nextBtn = doc.selectFirst(".text-center > a.btn_next");
//		String classText = nextBtn.className();
//		// 如果没有下页则不处理
//		if(classText == null || "".equals(classText.trim()) || classText.contains("hide")) {
//			return;
//		}
		HtmlAnchor btn = page.getFirstByXPath("//a[@class='btn_next']");
		String classText = btn == null ? null : btn.getAttribute("class");
		// 如果没有下页则不处理
		if (btn == null || classText == null || "".equals(classText.trim()) || classText.contains("hide")) {
			return;
		}
		// 方法一：触发下一页按钮点击事件
		btn.click();
		// 方法二：触发下一页按钮点击事件
//		page.executeJavaScript("document.querySelector('a.btn_next').click()");
		System.out.println("-------------请求下一页数据-------------");
//		webClient.waitForBackgroundJavaScript(1000 * 20); // 不需要
		printNewsTitle(webClient, page);
	}

}
