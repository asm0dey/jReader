package com.github.asm0dey.server.services;

import com.github.asm0dey.client.services.FeedService;
import com.github.asm0dey.server.dao.repositories.AuthorRepository;
import com.github.asm0dey.server.dao.repositories.FeedItemRepository;
import com.github.asm0dey.server.dao.repositories.FeedRepository;
import com.github.asm0dey.shared.domain.Author;
import com.github.asm0dey.shared.domain.Feed;
import com.github.asm0dey.shared.domain.FeedItem;
import org.apache.commons.httpclient.HttpURL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yarfraw.core.datamodel.ChannelFeed;
import yarfraw.core.datamodel.ItemEntry;
import yarfraw.core.datamodel.Person;
import yarfraw.core.datamodel.YarfrawException;
import yarfraw.io.CachedFeedReader;
import yarfraw.io.FeedReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: finkel
 * <p/>
 * Date: 16.03.13
 * <p/>
 * Time: 16:40
 */
@Service( "feedService" )
public class FeedServiceImpl implements FeedService {
	private static final Map<String, FeedReader> READER_MAP = new HashMap<String, FeedReader>();
	@Autowired
	FeedRepository feedRepository;
	@Autowired
	AuthorRepository authorRepository;
	@Autowired
	FeedItemRepository feedItemRepository;
	@Autowired
	MappingUtil mappingUtil;

	@Override
	public List<FeedItem> listItems( Feed feed ) {
		if ( feed.getId() == null ) {
			try {
				Feed savedFeed = feedRepository.saveAndFlush( feed );
				ChannelFeed channelFeed = getReader( feed.getUrl() ).readChannel();
				List<ItemEntry> channelItems = channelFeed.getItems();
				List<FeedItem> feedItems = new ArrayList<FeedItem>();
				for ( ItemEntry channelItem : channelItems ) {
					FeedItem item = new FeedItem();
					List<Person> authorOrCreator = channelItem.getAuthorOrCreator();
					if ( authorOrCreator != null ) {
						List<Author> authors = new ArrayList<Author>( authorOrCreator.size() );
						for ( Person person : authorOrCreator ) {
							Author author = new Author();
							author.setName( person.getName() );
							authors.add( author );
						}
						authorRepository.save( authors );
					}
					item.setTitle( channelItem.getTitleText() );
					System.out.println( channelItem.getPubDate() );
// item.setCreatedOn( Date.valueOf( channelItem.getPubDate() ) );
					item.setText( channelItem.getDescriptionOrSummaryText() );
					item.setFeed( savedFeed );
					feedItems.add( item );
				}
				List<FeedItem> savedItems = feedItemRepository.save( feedItems );
				List<FeedItem> items = savedFeed.getItems();
				if ( items == null )
					savedFeed.setItems( savedItems );
				else
					items.addAll( savedItems );
				savedFeed = feedRepository.saveAndFlush( savedFeed );
				return mappingUtil.remapCollection( savedItems, FeedItem.class );
			} catch ( YarfrawException e ) {
				e.printStackTrace(); // To change body of catch statement use File | Settings | File Templates.
			}
		} else {
			return mappingUtil.remapCollection( feedRepository.findOne( feed.getId() ).getItems(), FeedItem.class );
		}
		return null;
	}

	private FeedReader getReader( String url ) {
		FeedReader feedReader = READER_MAP.get( url );
		if ( feedReader == null ) {
			try {
				READER_MAP.put( url, new CachedFeedReader( new HttpURL( url ) ) );
			} catch ( YarfrawException e ) {
				e.printStackTrace(); // To change body of catch statement use File | Settings | File Templates.
			} catch ( IOException e ) {
				e.printStackTrace(); // To change body of catch statement use File | Settings | File Templates.
			}
		}
		return READER_MAP.get( url );
	}
}
