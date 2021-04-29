package com.medin.pharmacy.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class StringUtils {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerValidationUtil.class);

	private StringUtils() {
	}
	
	public static final String getRandomString() {
		return getRandomString(32, true, true);
	}

	public static final String getRandomString(int length, boolean chars, boolean numbers) {
		return RandomStringUtils.random(length, chars, numbers);
	}

	public static final String getRandomDigits(int length) {
		StringBuilder randomString = new StringBuilder();
		for (int i = 0; i < length; i++) {
			randomString.append(RandomUtils.nextInt(0,9));
		}
		return randomString.toString();
	}

}
