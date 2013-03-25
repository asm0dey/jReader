package com.github.asm0dey.client.views;

import com.github.asm0dey.client.presenters.MainPagePresenter;
import com.github.asm0dey.client.presenters.MainPageUiHandlers;
import com.github.asm0dey.shared.domain.Feed;
import com.github.asm0dey.shared.domain.FeedGroup;
import com.github.gwtbootstrap.client.ui.*;
import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.TextBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.ui.Label;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import java.util.Map;

import static com.github.asm0dey.client.presenters.MainPagePresenter.SLOT_feedItemsSlot;
import static com.google.gwt.dom.client.Style.Cursor.POINTER;

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
	/*
		@UiField
		Button addCategoryButton;
	*/
	@UiField
	HorizontalPanel categoryPanel;
	@UiField
	Button okCategoryButton;
	@UiField
	TextBox categoryName;
	@UiField
	NavLink addCategoryNavLink;
	@UiField
	NavLink importLink;
	@UiField
	ScrollPanel itemsScroller;
	@UiField
	VerticalPanel itemsPanel;
	@UiField
	NavLink addFeedNavLink;

	public MainPageViewImpl() {
		HTMLPanel rootElement = ourUiBinder.createAndBindUi( this );
		initWidget( rootElement );
		addCategoryNavLink.addClickHandler( new ClickHandler() {
			@Override
			public void onClick( ClickEvent event ) {
				final Modal modal = new Modal( true, true );
				final TextBox textBox = new TextBox();
				textBox.setPlaceholder( "Category Name:" );
				textBox.setWidth( "100%" );
				modal.add( textBox );
				modal.setWidth( 300 );
				ModalFooter modalFooter = new ModalFooter( new Button( "OK", new ClickHandler() {
					@Override
					public void onClick( ClickEvent event ) {
						String value = textBox.getValue();
						if ( value != null ) {
							getUiHandlers().addCategory( value );
							modal.hide();
						}
					}
				} ) );
				modal.add( modalFooter );
				modal.show();
			}
		} );
        addFeedNavLink.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                getUiHandlers().addNewSubscription();
            }
        });
	}

	@Override
	public void handleFeedGroups( Map<String, FeedGroup> result ) {
		accordion.clear();
		for ( Map.Entry<String, FeedGroup> stringFeedGroupEntry : result.entrySet() ) {
			AccordionGroup accordionGroup = new AccordionGroup();
			accordionGroup.setHeading( stringFeedGroupEntry.getKey() );
			FeedGroup value = stringFeedGroupEntry.getValue();
			if ( value.getFeeds() != null )
				for ( final Feed feed : value.getFeeds() ) {
					Label feedLabel = new Label( feed.getTitle() );
					feedLabel.addClickHandler( new ClickHandler() {
						@Override
						public void onClick( ClickEvent event ) {
							getUiHandlers().fetchItems( feed.getId() );
						}
					} );
					feedLabel.getElement().getStyle().setCursor( POINTER );
					accordionGroup.add( feedLabel );
				}
			accordion.add( accordionGroup );
		}
	}

	@UiHandler( { "okCategoryButton" } )
	public void handleClick( ClickEvent event ) {
		Object source = event.getSource();
		if ( source == okCategoryButton ) {
			getUiHandlers().addCategory( categoryName.getValue() );
			categoryPanel.setVisible( false );
		}
	}

	@Override
	public void addToSlot( Object slot, Widget content ) {
		if ( slot == SLOT_feedItemsSlot ) {
			itemsPanel.add( content );
		} else
			super.addToSlot( slot, content );
	}

	@Override
	public void setInSlot( Object slot, Widget content ) {
		if ( slot == SLOT_feedItemsSlot ) {
			itemsPanel.clear();
			if ( content != null )
				addToSlot( slot, content );
		} else
			super.setInSlot( slot, content );
	}

	interface MainPageViewImplUiBinder extends UiBinder<HTMLPanel, MainPageViewImpl> {
	}

}