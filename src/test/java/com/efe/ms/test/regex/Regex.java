package com.efe.ms.test.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class Regex {

	@Test
	public void test01() {
//		String str = "https://cbu01.alicdn.com/img/ibank/2019/652/228/12750822256_503992444.32x32.jpg";
		String str = "https://cbu01.alicdn.com/img/ibank/2019/652/228/12750822256_503992444.32x32_88888888888.jpg";
//		Pattern pattern = Pattern.compile("\\d{11}");
		Pattern pattern = Pattern.compile("[0-9]{11}");
		Matcher matcher = pattern.matcher(str);
		
		if(matcher.find()) {
			System.out.println(matcher.groupCount());
			System.out.println(matcher.group());
		}
	}
}
