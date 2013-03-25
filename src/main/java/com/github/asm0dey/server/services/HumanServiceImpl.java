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
import com.google.common.collect.Maps;
import org.dozer.Mapper;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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
    public Map<String,Long> listUserFeedGroupIds(Long userId) {
        Human human = humanRepository.findOne(userId);
        final Map<String,Long> map = newHashMap();
        for (Map.Entry<String, FeedGroup> stringFeedGroupEntry : human.getCategories().entrySet()) {
            map.put(stringFeedGroupEntry.getKey(),stringFeedGroupEntry.getValue().getId());
        }
        return map;
    }

    @Override
    public Map<String, FeedGroup> listUserFeedGroups(Long userId) {
        Human found = humanRepository.findOne(userId);
        return listUserFeedGroups(found);
    }

    private Map<String, FeedGroup> listUserFeedGroups(Human found) {
        Map<String, FeedGroup> categories = found.getCategories();
        HashMap<String,FeedGroup> destination = Maps.<String, FeedGroup>newHashMap();
        for (Map.Entry<String, FeedGroup> entry : categories.entrySet()) {
            destination.put(entry.getKey(),mapper.map(entry.getValue(),FeedGroup.class));
        }
        return destination;
    }

    @Override
    public Map<String, FeedGroup> addFeedGroup(Long userId, String feedGroupName) {
        FeedGroup feedGroup = new FeedGroup();
        Human human = humanRepository.findOne(userId);
        feedGroupRepository.save(feedGroup);
        human.getCategories().put(feedGroupName,feedGroup);
        humanRepository.save(human);
        return listUserFeedGroups(human);
    }

    private void initializeHumanWithDefaultSettings( Human human ) {
		FeedGroup defaultFeedGroup = new FeedGroup();
		String lentaUrl = "http://lenta.ru/rss";
		feedService.listItems( lentaUrl, 0 );
		defaultFeedGroup.setFeeds( newArrayList( feedRepository.findByUrl( lentaUrl ) ) );
		feedGroupRepository.save( defaultFeedGroup );
		Map<String,FeedGroup> map = newHashMap();
        map.put("Default",defaultFeedGroup);
		human.setCategories( map );
		human = humanRepository.save( human );
	}
}
