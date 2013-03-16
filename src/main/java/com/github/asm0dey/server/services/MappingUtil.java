package com.github.asm0dey.server.services;

import com.github.asm0dey.shared.domain.AbstractPojo;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * User: finkel
 * <p/>
 * Date: 16.03.13
 * <p/>
 * Time: 16:04
 */
@Transactional
public class MappingUtil {
	@Autowired
	Mapper mapper;

	public <T extends AbstractPojo> T remapForSerialization( T pojo, Class<T> clazz ) {
		return mapper.map( pojo, clazz );
	}

	public <T extends AbstractPojo> List<T> remapCollection( Iterable<T> collection, Class<T> clazz ) {
		ArrayList<T> list = new ArrayList<T>();
		for ( T t : collection ) {
			list.add( remapForSerialization( t, clazz ) );
		}
		return list;
	}
}
