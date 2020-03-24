package com.efe.ms.test.webmagic;

import com.efe.ms.test.ext.HttpClientDownloader;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 
 * webmagic官方demo
 * @2020年3月23日 下午3:45:58
 */
public class WebmagicDemoProcessor implements PageProcessor {

	private Site site = Site.me().setRetryTimes(3).setSleepTime(100);

	public static void main(String[] args) {
//		Spider.create(new GithubRepoProcessor()).addUrl("https://github.com/code4craft").thread(5).run();
		Spider.create(new WebmagicDemoProcessor()).setDownloader(new HttpClientDownloader())
				.addUrl("https://github.com/code4craft").thread(5).run();
	}

	@Override
	public void process(Page page) {
		page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/\\w+/\\w+)").all());
		page.putField("author", page.getUrl().regex("https://github\\.com/(\\w+)/.*").toString());
		page.putField("name", page.getHtml().xpath("//h1[@class='entry-title public']/strong/a/text()").toString());
		if (page.getResultItems().get("name") == null) {
			page.setSkip(true);
		}
		page.putField("readme", page.getHtml().xpath("//div[@id='readme']/tidyText()"));
		page.getHtml().toString();
	}

	@Override
	public Site getSite() {
		return site;
	}

}
