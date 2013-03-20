package com.github.asm0dey.client.services;

import com.github.asm0dey.shared.domain.Feed;
import com.github.asm0dey.shared.domain.FeedItem;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;

/**
 * User: finkel
 * <p/>
 * Date: 16.03.13
 * <p/>
 * Time: 16:38
 */
@RemoteServiceRelativePath( "springGwtServices/feedService" )
public interface FeedService extends RemoteService {
	public List<FeedItem> listItems( String url,int pageNum );
}
