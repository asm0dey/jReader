package com.github.asm0dey.server.services;

import com.github.asm0dey.client.services.HumanService;
import com.github.asm0dey.server.dao.repositories.FeedGroupRepository;
import com.github.asm0dey.server.dao.repositories.FeedRepository;
import com.github.asm0dey.server.dao.repositories.HumanRepository;
import com.github.asm0dey.shared.domain.Exception.AuthenticationException;
import com.github.asm0dey.shared.domain.Exception.CreateUserException;
import com.github.asm0dey.shared.domain.Feed;
import com.github.asm0dey.shared.domain.FeedGroup;
import com.github.asm0dey.shared.domain.FeedItem;
import com.github.asm0dey.shared.domain.Human;
import com.github.asm0dey.shared.opml.BodyBean;
import com.github.asm0dey.shared.opml.OpmlBean;
import com.github.asm0dey.shared.opml.OutlineBean;
import com.google.common.collect.Maps;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import org.dozer.Mapper;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;

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
	public List<FeedItem> listAllFeedItems( Human human ) {
		List<FeedItem> source = humanRepository.listAllItems( new Pageable() {
			@Override
			public int getPageNumber() {
				return 0; // To change body of implemented methods use File | Settings | File Templates.
			}

			@Override
			public int getPageSize() {
				return 5; // To change body of implemented methods use File | Settings | File Templates.
			}

			@Override
			public int getOffset() {
				return 0; // To change body of implemented methods use File | Settings | File Templates.
			}

			@Override
			public Sort getSort() {
				return null; // To change body of implemented methods use File | Settings | File Templates.
			}
		} );
		return mapper.map( source, List.class );
	}

	@Override
	public Map<String, Long> listUserFeedGroupIds( Long userId ) {
		Human human = humanRepository.findOne( userId );
		final Map<String, Long> map = newHashMap();
		for ( Map.Entry<String, FeedGroup> stringFeedGroupEntry : human.getCategories().entrySet() ) {
			map.put( stringFeedGroupEntry.getKey(), stringFeedGroupEntry.getValue().getId() );
		}
		return map;
	}

	@Override
	public Map<String, FeedGroup> listUserFeedGroups( Long userId ) {
		Human found = humanRepository.findOne( userId );
		return listUserFeedGroups( found );
	}

	private Map<String, FeedGroup> listUserFeedGroups( Human found ) {
		Map<String, FeedGroup> categories = found.getCategories();
		HashMap<String, FeedGroup> destination = Maps.<String, FeedGroup> newHashMap();
		for ( Map.Entry<String, FeedGroup> entry : categories.entrySet() ) {
			destination.put( entry.getKey(), mapper.map( entry.getValue(), FeedGroup.class ) );
		}
		return destination;
	}

	@Override
	public Map<String, FeedGroup> addFeedGroup( Long userId, String feedGroupName ) {
		FeedGroup feedGroup = new FeedGroup();
		Human human = humanRepository.findOne( userId );
		feedGroupRepository.save( feedGroup );
		human.getCategories().put( feedGroupName, feedGroup );
		humanRepository.save( human );
		return listUserFeedGroups( human );
	}

	@Override
	public Map<String, FeedGroup> addFeedToFeedGroup( String url, String groupName, Long userId ) {
		Human human = humanRepository.findOne( userId );
		FeedGroup feedGroup = human.getCategories().get( groupName );
		Feed feed = feedRepository.findByUrl( url );
		if ( feed == null ) {
			feedService.listItems( url, 0 );
			feed = feedRepository.findByUrl( url );
		}
		feedGroup.getFeeds().add( feed );
		feedGroupRepository.save( feedGroup );
		human.getCategories().put( groupName, feedGroup );
		humanRepository.save( human );
		return listUserFeedGroups( human );
	}

	@Override
	public Map<String, FeedGroup> importOpml( String opml, Long userId ) {
		OpmlBean opmlBean = (OpmlBean) new XStream( new JettisonMappedXmlDriver() ).fromXML( opml );
		List<OutlineBean> groupBeans = findFeedGroups( opmlBean );
		Human human = humanRepository.findOne( userId );
		addNeededGroupsToHuman( groupBeans, human );
		fillGroupsWithFeeds( groupBeans, opmlBean.getBodyBean(), human );
		return listUserFeedGroups( userId );
	}

	private void fillGroupsWithFeeds( List<OutlineBean> groupBeans, BodyBean bodyBean, Human human ) {
		for ( OutlineBean groupBean : groupBeans ) {
			boolean feedGroupChanged = false;
			FeedGroup feedGroup = human.getCategories().get( groupBean.getTitle() );
			for ( OutlineBean outlineBeanItem : groupBean.getOutlineBeans() ) {
				feedGroupChanged = addOutlinBeanItemIfNeeded( feedGroup, outlineBeanItem );
			}
			if ( feedGroupChanged )
				feedGroupRepository.saveAndFlush( feedGroup );
		}
		FeedGroup feedGroup = human.getCategories().get( "Default" );
		boolean addedItemsToDefaultGroup = false;
		for ( OutlineBean outlineBeanItem : bodyBean.getBeans() ) {
			if ( addOutlinBeanItemIfNeeded( feedGroup, outlineBeanItem ) )
				addedItemsToDefaultGroup = true;
		}
		if ( addedItemsToDefaultGroup )
			feedGroupRepository.saveAndFlush( feedGroup );
	}

	private boolean addOutlinBeanItemIfNeeded( FeedGroup feedGroup, OutlineBean outlineBeanItem ) {
		boolean itemsAdded = false;
		if ( !outlineBeanItem.isCategory() ) {
			String url = outlineBeanItem.getXmlUrl();
			Feed feed = feedRepository.findByUrl( url );
			if ( feed == null ) {
				feedService.listItems( url, 0 );
				feed = feedRepository.findByUrl( url );
			}
			Set<Feed> feeds = feedGroup.getFeeds();
			if ( feeds == null ) {
				feeds = newHashSet();
				feedGroup.setFeeds( feeds );
			}
			if ( feed != null && !feeds.contains( feed ) ) {
				feedGroup.add( feed );
				itemsAdded = true;
			}
		}
		return itemsAdded;
	}

	private void addNeededGroupsToHuman( List<OutlineBean> groupBeans, Human human ) {
		boolean categoriesAdded = false;
		for ( OutlineBean groupBean : groupBeans ) {
			if ( !human.getCategories().containsKey( groupBean.getTitle() ) ) {
				FeedGroup feedGroup = new FeedGroup();
				feedGroupRepository.save( feedGroup );
				human.getCategories().put( groupBean.getTitle(), feedGroup );
				categoriesAdded = true;
			}
		}
		if ( categoriesAdded ) {
			humanRepository.saveAndFlush( human );
		}
	}

	private List<OutlineBean> findFeedGroups( OpmlBean opmlBean ) {
		List<OutlineBean> beans = newArrayList();
		for ( OutlineBean bean : opmlBean.getBodyBean().getBeans() ) {
			beans.addAll( extractGroupBeans( bean ) );
		}
		return beans;
	}

	private List<OutlineBean> extractGroupBeans( OutlineBean bean ) {
		List<OutlineBean> outlineBeans = newArrayList();
		if ( bean.isCategory() ) {
			outlineBeans.add( bean );
			for ( OutlineBean outlineBean : bean.getOutlineBeans() ) {
				outlineBeans.addAll( extractGroupBeans( outlineBean ) );
			}
		}
		return outlineBeans;
	}

	private void initializeHumanWithDefaultSettings( Human human ) {
		FeedGroup defaultFeedGroup = new FeedGroup();
		String lentaUrl = "http://lenta.ru/rss";
		feedService.listItems( lentaUrl, 0 );
		defaultFeedGroup.setFeeds( newHashSet( feedRepository.findByUrl( lentaUrl ) ) );
		feedGroupRepository.save( defaultFeedGroup );
		Map<String, FeedGroup> map = newHashMap();
		map.put( "Default", defaultFeedGroup );
		human.setCategories( map );
		human = humanRepository.save( human );
	}
}
