package com.github.asm0dey.server.services;

import com.github.asm0dey.shared.domain.AbstractPojo;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User: finkel
 * <p/>
 * Date: 16.03.13
 * <p/>
 * Time: 16:04
 */
public class MappingUtil {
	@Autowired
	Mapper mapper;

	public <T extends AbstractPojo> T remapForSerialization( T pojo, Class<T> clazz ) {
		return mapper.map( pojo, clazz );
	}
}
