package com.efe.ms.test.regex;

import java.nio.charset.Charset;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONPath;
import com.efe.ms.test.util.ClassPathResourceUtils;

public class HtmlParse {
	
	
	@Test
	public void extraJSCode() throws Exception{
		String path = "pages/590449991333.html";
		String html = ClassPathResourceUtils.getResourceAsString(path,Charset.forName("gbk"));
//		System.out.println(html);
		
		String dcFlag = "var iDetailConfig =",
			   ddFlag = "var iDetailData =",
			   ddaFlag = "iDetailData.allTagIds =";
		int dcIdx = html.indexOf(dcFlag),
			ddIdx = html.indexOf(ddFlag),
			ddaIdx = html.indexOf(ddaFlag);
		String dcStr = html.substring(dcIdx + dcFlag.length(), ddIdx).trim();
		dcStr = dcStr.endsWith(";") ? dcStr.substring(0,dcStr.lastIndexOf(";")) : dcStr;
		String ddStr = html.substring(ddIdx + ddFlag.length(), ddaIdx).trim();
		ddStr = ddStr.endsWith(";") ? ddStr.substring(0,ddStr.lastIndexOf(";")) : ddStr;
		System.out.println("--------------iDetailConfig JSON String----------------");
		System.out.println(dcStr);
		System.out.println("--------------iDetailData JSON String----------------");
		System.out.println(ddStr);
		System.out.println("--------------iDetailConfig JSON----------------");
		System.out.println(JSON.parseObject(dcStr));
		System.out.println("--------------iDetailData JSON----------------");
		System.out.println(JSON.parseObject(ddStr));
		System.out.println("--------------JSON Path----------------");
		System.out.println(JSONPath.read(ddStr, "$.sku.skuMap"));
		System.out.println(JSONPath.eval(JSON.parseObject(ddStr), "$.sku.skuMap"));
		
		Document doc = Jsoup.parse(html);
		Element ele = doc.select("meta[property=\"og:image\"]").get(0);
		String mainImage = ele.attr("content");
		System.out.println("-------------main image url---------------");
		System.out.println(mainImage); 
		ele = doc.select(".mod-detail-title h1.d-title").get(0);
		String title = ele.ownText();
		System.out.println("-------------main title---------------");
		System.out.println(title);
	}

}
