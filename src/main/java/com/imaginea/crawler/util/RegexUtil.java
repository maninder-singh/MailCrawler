package com.imaginea.crawler.util;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {
	
	public static Pattern createPattern(List<String> patternParameter){
		Pattern pattern;
		
		StringBuilder patternStrBuilder = new StringBuilder();
		for(String parameter : patternParameter){
			patternStrBuilder.append(parameter + "|");
		}
		if(patternStrBuilder.length() > 0){
			patternStrBuilder.replace(patternStrBuilder.length()-1, patternStrBuilder.length() -1,"");
		}
		System.out.println("patternStrBuilder : " + patternStrBuilder );
		pattern = Pattern.compile(patternStrBuilder.toString());
		return pattern;
	}

	public static boolean isPatternFound(Pattern pattern , String content){
		boolean isPatternFound = false;
		Matcher matcher;
		
		matcher = pattern.matcher(content);
		if(matcher.find()){
			isPatternFound = true;
		}
		return isPatternFound;
	}
}
