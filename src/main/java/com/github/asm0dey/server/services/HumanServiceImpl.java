package com.github.asm0dey.server.services;

import com.github.asm0dey.client.services.HumanService;
import com.github.asm0dey.server.dao.repositories.FeedGroupRepository;
import com.github.asm0dey.server.dao.repositories.FeedRepository;
import com.github.asm0dey.server.dao.repositories.HumanRepository;
import com.github.asm0dey.shared.domain.Exception.AuthenticationException;
import com.github.asm0dey.shared.domain.Exception.CreateUserException;
import com.github.asm0dey.shared.domain.FeedGroup;
import com.github.asm0dey.shared.domain.FeedItem;
import com.github.asm0dey.shared.domain.Human;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.dozer.Mapper;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;

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
	Mapper mapper;
	@Autowired
	FeedServiceImpl feedService;
	@Autowired
	FeedRepository feedRepository;
	@Autowired
	FeedGroupRepository feedGroupRepository;

	@Override
	public Long createUser( Human human ) throws AuthenticationException, CreateUserException {
		Human found = humanRepository.findByEmail( human.getEmail() );
		if ( found == null ) {
			String hashed = BCrypt.hashpw( human.getPasswordHash(), BCrypt.gensalt() );
			human.setPasswordHash( hashed );

			try {

				found = humanRepository.save( human );
				initializeHumanWithDefaultSettings( found );

			} catch ( Exception e ) {
				throw new CreateUserException( "Unable to create user", e );
			}
		} else {
			boolean checkpw = BCrypt.checkpw( human.getPasswordHash(), found.getPasswordHash() );
			if ( !checkpw )
				throw new AuthenticationException( "login and/or password incorrect" );
		}
		return found.getId();
	}

    @Override
    public List<FeedItem> listAllFeedItems(Human human) {
        List<FeedItem> source = humanRepository.listAllItems(new Pageable() {
            @Override
            public int getPageNumber() {
                return 0;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public int getPageSize() {
                return 5;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public int getOffset() {
                return 0;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public Sort getSort() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }
        });
        return mapper.map(source,List.class);
    }

    @Override
    public Map<String,Long> listUserFeedGroups(Long userId) {
        Human human = humanRepository.findOne(userId);
        List<FeedGroup> source = humanRepository.listFeedGroups(userId);
        final Map<String,Long> map = newHashMap();
        for (FeedGroup feedGroup : source) {
            map.put(feedGroup.getName(),feedGroup.getId());
        }
        return map;
    }

    private void initializeHumanWithDefaultSettings( Human human ) {
		FeedGroup defaultFeedGroup = new FeedGroup();
		defaultFeedGroup.setName( "Default" );
		defaultFeedGroup.setOwner( human );
		String lentaUrl = "http://lenta.ru/rss";
		feedService.listItems( lentaUrl, 0 );
		defaultFeedGroup.setFeeds( newArrayList( feedRepository.findByUrl( lentaUrl ) ) );
		feedGroupRepository.save( defaultFeedGroup );
		ArrayList<FeedGroup> categories = newArrayList( defaultFeedGroup );
		human.setCategories( categories );
		human = humanRepository.save( human );
	}
}
