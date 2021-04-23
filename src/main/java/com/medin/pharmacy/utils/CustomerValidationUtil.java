package com.medin.pharmacy.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomerValidationUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerValidationUtil.class);

	private CustomerValidationUtil() {
	}

	/**
	 * Validate Mobile Number Format
	 * 
	 * @param mobile
	 * @return
	 * @throws BusinessException
	 */
	public static boolean validateMobileNumberFormat(String mobile) throws BusinessException {
		if (StringUtils.isBlank(mobile)) {
			throw new BusinessException("Mobile Number is Mandatory");
		}
		if (!mobile.matches("^[1-9][0-9]{9}$")) {
			throw new BusinessException("Invalid Mobile Number");
		}
		return true;
	}

	/**
	 * Validate Mobile Number Format
	 * 
	 * @param mobile
	 * @return
	 * @throws LappException
	 */
	public static boolean validateEmailFormat(String email) throws BusinessException {
		if (StringUtils.isBlank(email)) {
			throw new BusinessException("Email is Mandatory");
		}
		if (!EmailValidator.getInstance().isValid(email)) {
			throw new BusinessException("Invalid email format");
		}
		return true;
	}

}