package com.drofff.manageday.utils;

import java.util.Map;

public class FormattingUtils {

	private static final String VARIABLE_FORMAT_BOUND = "\\*";

	private FormattingUtils() {}

	public static String putVarsIntoText(Map<String, String> vars, String text) {
		for(Map.Entry<String, String> var : vars.entrySet()) {
			String varName = var.getKey();
			String marker = VARIABLE_FORMAT_BOUND + varName + VARIABLE_FORMAT_BOUND;
			text = text.replaceAll(marker, var.getValue());
		}
		return text;
	}

}
