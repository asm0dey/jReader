package com.github.asm0dey.client.views;

import com.github.asm0dey.client.presenters.MainPagePresenter;
import com.github.gwtbootstrap.client.ui.AccordionGroup;
import com.github.gwtbootstrap.client.ui.event.ShowEvent;
import com.github.gwtbootstrap.client.ui.event.ShowHandler;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import java.util.Map;

/**
 * User: finkel
 * <p/>
 * Date: 22.03.13
 * <p/>
 * Time: 17:42
 */
public class MainPageViewImpl extends ViewWithUiHandlers<MainPageUiHandlers> implements MainPagePresenter.MainPageView {
	private static MainPageViewImplUiBinder ourUiBinder = GWT.create( MainPageViewImplUiBinder.class );
	@UiField
	com.github.gwtbootstrap.client.ui.Accordion accordion;

	public MainPageViewImpl() {
		HTMLPanel rootElement = ourUiBinder.createAndBindUi( this );
		initWidget( rootElement );
	}

	@Override
	public void handleFeedGroups( Map<String, Long> result ) {
		for ( final Map.Entry<String, Long> stringLongEntry : result.entrySet() ) {
			AccordionGroup accordionGroup = new AccordionGroup();
			accordionGroup.setHeading( stringLongEntry.getKey() );
			accordionGroup.addShowHandler( new ShowHandler() {
				@Override
				public void onShow( ShowEvent showEvent ) {
					getUiHandlers().loadFeeds( stringLongEntry.getValue() );
				}
			} );
			accordion.add( accordionGroup );
		}
	}

	interface MainPageViewImplUiBinder extends UiBinder<HTMLPanel, MainPageViewImpl> {
	}

}