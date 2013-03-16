package com.github.asm0dey.server.services;

import com.github.asm0dey.client.services.HumanService;
import com.github.asm0dey.server.dao.repositories.HumanRepository;
import com.github.asm0dey.shared.domain.Human;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User: finkel
 * <p/>
 * Date: 16.03.13
 * <p/>
 * Time: 14:55
 */
@Service( "humanService" )
public class HumanServiceImpl implements HumanService {
	@Autowired
	HumanRepository humanRepository;
	@Autowired
	private MappingUtil MAPPING_UTIL;

	@Override
	public Human createUser( Human human ) {
		Human pojo = humanRepository.saveAndFlush( human );
		return MAPPING_UTIL.remapForSerialization( pojo, Human.class );
	}
}
