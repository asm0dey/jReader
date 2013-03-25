package com.github.asm0dey.client.views;

import com.github.asm0dey.client.presenters.FeedItemPresenter;
import com.github.asm0dey.client.presenters.FeedItemUiHandlers;
import com.github.gwtbootstrap.client.ui.Label;
import com.github.gwtbootstrap.client.ui.Paragraph;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FocusPanel;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import java.util.Date;

/**
 * User: finkel
 * <p/>
 * Date: 25.03.13
 * <p/>
 * Time: 15:11
 */
public class FeedItemViewImpl extends ViewWithUiHandlers<FeedItemUiHandlers> implements FeedItemPresenter.FeedItemView {
	private static FeedItemViewImplUiBinder ourUiBinder = GWT.create( FeedItemViewImplUiBinder.class );
	private final FocusPanel rootElement;
	@UiField
	Label date;
	@UiField
	Paragraph content;
	@UiField
	HeadingElement header;
	private boolean isRead = false;

	public FeedItemViewImpl() {
		rootElement = ourUiBinder.createAndBindUi( this );
		initWidget( rootElement );
		rootElement.addFocusHandler( new FocusHandler() {
			@Override
			public void onFocus( FocusEvent event ) {
				if ( !isRead ) {
					getUiHandlers().markRead();
					rootElement.removeStyleName( "unread" );
					rootElement.addStyleName( "read" );
				}
			}
		} );
	}

	@Override
	public void setHeader( String title ) {
		header.setInnerText( title );
	}

	@Override
	public void setDate( Date createdOn ) {
		date.setText( DateTimeFormat.getFormat( DateTimeFormat.PredefinedFormat.DATE_TIME_MEDIUM ).format( createdOn ) );
	}

	@Override
	public void setText( String text ) {
		content.setText( text );
	}

	interface FeedItemViewImplUiBinder extends UiBinder<FocusPanel, FeedItemViewImpl> {
	}
}