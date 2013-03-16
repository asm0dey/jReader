package com.github.asm0dey.client;

import com.github.asm0dey.client.services.FeedServiceAsync;
import com.github.asm0dey.shared.domain.Feed;
import com.github.asm0dey.shared.domain.FeedItem;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import java.util.List;

/**
 * User: finkel
 * <p/>
 * Date: 16.03.13
 * <p/>
 * Time: 14:31
 */
public class Main implements EntryPoint {
	public void onModuleLoad() {
		Feed feed = new Feed();
		feed.setUrl( "http://www.lostfilm.tv/rssdd.xml" );
		FeedServiceAsync.Util.getInstance().listItems( feed, new AsyncCallback<List<FeedItem>>() {
			@Override
			public void onFailure( Throwable caught ) {
				RootLayoutPanel.get().add( new Label( caught.getMessage() ) );
			}

			@Override
			public void onSuccess( List<FeedItem> result ) {
				VerticalPanel widgets = new VerticalPanel();
				for ( FeedItem item : result ) {
					widgets.add( new HTML( "<b>" + item.getTitle() + "</b><br/>" + item.getText() ) );
				}
				RootLayoutPanel.get().add( widgets );
			}
		} );

	}
}
