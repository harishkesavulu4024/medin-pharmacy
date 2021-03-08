package com.medin.pharmacy.utils;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.medin.pharmacy.dto.BaseDomainDTO;
import com.medin.pharmacy.entities.BaseEntity;

public class BeanUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(BeanUtil.class);

	private BeanUtil() {
	}

	public static void copyDAOtoDTO(BaseEntity entity, BaseDomainDTO dto) {
		try {
			BeanUtils.copyProperties(dto, entity);
		} catch (IllegalAccessException | InvocationTargetException e) {
			String message = "Unable to copy properties from DAO : " + entity + " to DTO : " + dto;
			LOGGER.error(message, e);
			throw new BusinessException(message, e);
		}
	}

	public static void copyDTOtoDAO(BaseDomainDTO dto, BaseEntity entity) {
		try {
			BeanUtils.copyProperties(entity, dto);
		} catch (IllegalAccessException | InvocationTargetException e) {
			String message = "Unable to copy properties from DTO : " + dto + " to DAO : " + entity;
			LOGGER.error(message, e);
			throw new BusinessException(message, e);
		}
	}

	public static void copyDTO(BaseDomainDTO src, BaseDomainDTO dest) {
		try {
			BeanUtils.copyProperties(dest, src);
		} catch (IllegalAccessException | InvocationTargetException e) {
			String message = "Unable to copy properties from DTO : " + src + " to DTO : " + dest;
			LOGGER.error(message, e);
			throw new BusinessException(message, e);
		}
	}
	
	public static void copyProperties(Serializable src, Serializable dest) {
		try {
			BeanUtils.copyProperties(dest, src);
		} catch (IllegalAccessException | InvocationTargetException e) {
			String message = "Unable to copy properties from DTO : " + src + " to DTO : " + dest;
			LOGGER.error(message, e);
			throw new BusinessException(message, e);
		}
	}

}