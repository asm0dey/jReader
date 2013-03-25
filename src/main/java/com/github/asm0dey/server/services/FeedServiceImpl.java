package com.github.asm0dey.server.services;

import com.github.asm0dey.client.services.FeedService;
import com.github.asm0dey.server.dao.repositories.*;
import com.github.asm0dey.shared.domain.Feed;
import com.github.asm0dey.shared.domain.FeedItem;
import com.github.asm0dey.shared.domain.Human;
import com.github.asm0dey.shared.domain.HumanFeedItem;
import org.dozer.Mapper;
import org.horrabin.horrorss.RssFeed;
import org.horrabin.horrorss.RssItemBean;
import org.horrabin.horrorss.RssParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;

/**
 * User: finkel
 * <p/>
 * Date: 16.03.13
 * <p/>
 * Time: 16:40
 */
@Service( "feedService" )
public class FeedServiceImpl implements FeedService {
	private static final Map<String, RssParser> PARSERS = newHashMap();
	private static final Map<String, String> ENCODINGS = newHashMap();
	@Autowired
	FeedRepository feedRepository;
	@Autowired
	FeedItemRepository feedItemRepository;
	@Autowired
	Mapper mapper;
	@Autowired
	FeedGroupRepository feedGroupRepository;
	@Autowired
	HumanRepository humanRepository;
	@Autowired
	HumanFeedItemRepository humanFeedItemRepository;

	private Feed generateFeedByURL( String url, RssParser parser ) throws Exception {
		RssFeed load = parser.load();
		Feed foundFeed = new Feed();
		foundFeed.setTitle( load.getChannel().getTitle() );
		foundFeed.setUrl( url );
		foundFeed.setLastUpdateDate( new Date() );
		foundFeed.setCharset( ENCODINGS.get( url ) );
		foundFeed.setImageUrl( load.getImage().getUrl() );
		foundFeed = feedRepository.save( foundFeed );

		return foundFeed;
	}

	private String detectEncoding( String url ) throws ParserConfigurationException, IOException, SAXException {
		InputStream inputStream = new BufferedInputStream( new URL( url ).openStream() );
		Document parse = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse( inputStream );
		try {
			return parse.getXmlEncoding();
		} finally {
			inputStream.close();
		}
	}

	private RssParser getParser( String url ) throws IOException, SAXException, ParserConfigurationException {
		RssParser parser = PARSERS.get( url );
		if ( parser == null ) {
			parser = new RssParser( url );
			String charset = detectEncoding( url );
			parser.setCharset( charset );
			PARSERS.put( url, parser );
			ENCODINGS.put( url, charset );
		}
		return parser;
	}

	@Transactional
	@Override
	public List<FeedItem> listItems( String url, final int pageNum ) {
		Feed foundFeed = feedRepository.findByUrl( url );
		if ( foundFeed != null ) {
			List<FeedItem> items = feedItemRepository.findByFeed_IdOrderByCreatedOnDesc( foundFeed.getId(), new Pageable() {
				@Override
				public int getPageNumber() {
					return pageNum; // To change body of implemented methods use File | Settings | File Templates.
				}

				@Override
				public int getPageSize() {
					return 30; // To change body of implemented methods use File | Settings | File Templates.
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
			return mapper.map( items, List.class );
		} else {
			try {
				RssParser parser = getParser( url );
				foundFeed = generateFeedByURL( url, parser );
				List<FeedItem> items = newArrayList();
				if ( foundFeed.getItems() == null )
					foundFeed.setItems( newHashSet( items ) );
				for ( RssItemBean rssItemBean : parser.load().getItems() ) {
					FeedItem feedItem = new FeedItem( rssItemBean.getPubDate(), rssItemBean.getDescription(), rssItemBean.getTitle(),
							rssItemBean.getLink() );
					feedItem.setAuthor( rssItemBean.getAuthor() );
					feedItem.setFeed( foundFeed );
					items.add( feedItem );
				}
				items = feedItemRepository.save( items );
				return mapper.map( items, List.class );
			} catch ( Exception e ) {
				e.printStackTrace(); // To change body of catch statement use File | Settings | File Templates.
			}

		}
		return null;
	}

	@Transactional
	@Override
	public Map<String, Long> listFeeds( Long feedGroupId ) {
		List<Feed> feeds = feedGroupRepository.listFeeds( feedGroupId );
		Map<String, Long> map = newHashMap();
		for ( Feed feed : feeds ) {
			map.put( feed.getTitle(), feed.getId() );
		}
		return map;
	}

	@Override
	public List<FeedItem> listUnreadFeedItems( Long feedId, Long humanId, final int pageNum ) {
		List<FeedItem> feedItems = humanRepository.listUnreadItems( feedId, humanId, new Pageable() {
			@Override
			public int getPageNumber() {
				return pageNum;
			}

			@Override
			public int getPageSize() {
				return 10;
			}

			@Override
			public int getOffset() {
				return getPageSize() * getPageNumber();
			}

			@Override
			public Sort getSort() {
				return null;
			}
		} );
		return mapper.map( feedItems, List.class );
	}

	@Override
	@Transactional( )
	public void setItemRead( Long humanId, Long itemId ) {
		Human foundHuman = humanRepository.findOne( humanId );
		FeedItem foundItem = feedItemRepository.findOne( itemId );
		HumanFeedItem humanFeedItem = humanFeedItemRepository.findByHumanAndFeedItem( foundHuman, foundItem );
		if ( humanFeedItem == null ) {
			humanFeedItem = new HumanFeedItem();
			humanFeedItem.setHuman( foundHuman );
			humanFeedItem.setFeedItem( foundItem );
		}
		if ( !humanFeedItem.isRead() )
			humanFeedItem.setRead( true );
		humanFeedItemRepository.save( humanFeedItem );
	}
}
