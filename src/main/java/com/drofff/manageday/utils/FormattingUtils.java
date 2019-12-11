package com.drofff.manageday.utils;

import java.util.Map;

import com.drofff.manageday.entity.RGBColor;

public class FormattingUtils {

	private static final String VARIABLE_FORMAT_BOUND = "\\*";
	private static final String HTML_COLOR_PREFIX = "#";

	private FormattingUtils() {}

	public static String putVarsIntoText(Map<String, String> vars, String text) {
		for(Map.Entry<String, String> var : vars.entrySet()) {
			String varName = var.getKey();
			String marker = VARIABLE_FORMAT_BOUND + varName + VARIABLE_FORMAT_BOUND;
			text = text.replaceAll(marker, var.getValue());
		}
		return text;
	}

	public static String convertRgbColorToHtmlColorStr(RGBColor rgbColor) {
		String htmlColor = HTML_COLOR_PREFIX;
		htmlColor += asHexString(rgbColor.getR());
		htmlColor += asHexString(rgbColor.getG());
		htmlColor += asHexString(rgbColor.getB());
		return htmlColor;
	}

	private static String asHexString(int number) {
		String hexStr = Integer.toHexString(number);
		if(hexStr.length() == 1) {
			hexStr = "0" + hexStr;
		}
		return hexStr;
	}

}
