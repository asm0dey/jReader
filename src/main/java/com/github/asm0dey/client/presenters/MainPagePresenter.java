package com.github.asm0dey.client.presenters;

import com.github.asm0dey.client.LoggedInGatekeeper;
import com.github.asm0dey.client.NameTokens;
import com.github.asm0dey.client.services.FeedServiceAsync;
import com.github.asm0dey.client.services.HumanServiceAsync;
import com.github.asm0dey.shared.domain.FeedGroup;
import com.github.asm0dey.shared.domain.FeedItem;
import com.github.gwtbootstrap.client.ui.AccordionGroup;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.*;

import java.util.List;
import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;
import static com.google.gwt.dom.client.Style.Cursor.POINTER;

/**
 * User: finkel
 * <p/>
 * Date: 22.03.13
 * <p/>
 * Time: 17:36
 */
public class MainPagePresenter extends Presenter<MainPagePresenter.MainPageView, MainPagePresenter.MainPageProxy> implements MainPageUiHandlers {
	public static final Object SLOT_feedItemsSlot = new Object();
	private final Provider<FeedItemPresenter> feedItemPresenterProvider;
	@Inject
	PlaceManager placeManager;
	private Long userId;
	private Map<AccordionGroup, Map<String, Long>> groupCache = newHashMap();
	private int pageNum = 0;

	/**
	 * Creates a {@link com.gwtplatform.mvp.client.Presenter} that uses automatic binding. This will only work when instantiating this object using
	 * Guice/GIN dependency injection. See {@link com.gwtplatform.mvp.client.HandlerContainerImpl#HandlerContainerImpl()} for more details on
	 * automatic binding.
	 * 
	 * @param eventBus
	 *            The {@link com.google.web.bindery.event.shared.EventBus}.
	 * @param view
	 *            The {@link com.gwtplatform.mvp.client.View}.
	 * @param proxy
	 *            The {@link com.gwtplatform.mvp.client.proxy.Proxy}.
	 */
	@Inject
	public MainPagePresenter( EventBus eventBus, MainPageView view, MainPageProxy proxy, Provider<FeedItemPresenter> feedItemPresenterProvider ) {
		super( eventBus, view, proxy );
		this.feedItemPresenterProvider = feedItemPresenterProvider;
		getView().setUiHandlers( this );

	}

	/**
	 * Lifecycle method called whenever this presenter is about to be revealed.
	 * <p/>
	 * <b>Important:</b> Make sure you call your superclass {@link #onReveal()} if you override. Also, do not call directly, see
	 * {@link com.gwtplatform.mvp.client.PresenterWidget} for more details on lifecycle methods.
	 * <p/>
	 * You should override this method to perform any action or initialisation that needs to be done when the presenter is revealed. Any
	 * initialisation you perform here should be taken down in {@link #onHide()}.
	 * <p/>
	 * Information that needs to be updated whenever the user navigates should be refreshed in {@link #onReset()}.
	 * <p/>
	 * In a presenter hierarchy, this method is called top-down: first on the parent presenters, then on the children.
	 */
	@Override
	protected void onReveal() {
		HumanServiceAsync.Util.getInstance().listUserFeedGroups( userId, new AsyncCallback<Map<String, FeedGroup>>() {
			@Override
			public void onFailure( Throwable caught ) {

			}

			@Override
			public void onSuccess( Map<String, FeedGroup> result ) {
				getView().handleFeedGroups( result );
			}
		} );
		super.onReveal();
	}

	/**
	 * Prepare the state of the presenter given the information contained in the {@link com.gwtplatform.mvp.client.proxy.PlaceRequest}. This method is
	 * called when the {@link com.gwtplatform.mvp.client.proxy.PlaceManager PlaceManager} navigates to this
	 * {@link com.gwtplatform.mvp.client.Presenter}. You should override the method to extract any parameters you need from the request. Make sure you
	 * call your parent's {@link #prepareFromRequest} method.
	 * <p/>
	 * If your presenter needs to fetch some information from the server while preparing itself, consider using manual reveal. See
	 * {@link #useManualReveal()}.
	 * <p/>
	 * If your presenter does not handle any parameter and does not want to fetch extra information, then there is no need to override this method.
	 * 
	 * @param request
	 *            The {@link com.gwtplatform.mvp.client.proxy.PlaceRequest}.
	 */
	@Override
	public void prepareFromRequest( PlaceRequest request ) {
		super.prepareFromRequest( request );
		String uid = request.getParameter( "uid", null );
		if ( uid == null )
			placeManager.revealDefaultPlace();
		else
			userId = Long.valueOf( uid );

	}

	@Override
	public void loadFeeds( Long value, final AccordionGroup accordionGroup ) {
		Map<String, Long> map = groupCache.get( accordionGroup );
		if ( map != null )
			addItemsToAccordionGroup( map, accordionGroup );
		else
			FeedServiceAsync.Util.getInstance().listFeeds( value, new AsyncCallback<Map<String, Long>>() {
				@Override
				public void onFailure( Throwable caught ) {

				}

				@Override
				public void onSuccess( Map<String, Long> result ) {
					groupCache.put( accordionGroup, result );
					addItemsToAccordionGroup( result, accordionGroup );
				}
			} );
	}

	@Override
	public void addCategory( String value ) {
		HumanServiceAsync.Util.getInstance().addFeedGroup( userId, value, new AsyncCallback<Map<String, FeedGroup>>() {
			@Override
			public void onFailure( Throwable caught ) {

			}

			@Override
			public void onSuccess( Map<String, FeedGroup> result ) {
				getView().handleFeedGroups( result );
			}
		} );
	}

	@Override
	public void fetchItems( Long feedId ) {
		FeedServiceAsync.Util.getInstance().listUnreadFeedItems( feedId, userId, pageNum, new AsyncCallback<List<FeedItem>>() {
			@Override
			public void onFailure( Throwable caught ) {

			}

			@Override
			public void onSuccess( List<FeedItem> result ) {
				setInSlot( SLOT_feedItemsSlot, null );
				for ( FeedItem item : result ) {
					FeedItemPresenter feedItemPresenter = feedItemPresenterProvider.get();
					feedItemPresenter.setSource( item );
                    feedItemPresenter.setUid(userId);
					addToSlot( SLOT_feedItemsSlot, feedItemPresenter );
				}
			}
		} );
	}

	private void addItemsToAccordionGroup( Map<String, Long> result, AccordionGroup accordionGroup ) {
		for ( Map.Entry<String, Long> stringLongEntry : result.entrySet() ) {
			Label label = new Label( stringLongEntry.getKey() );
			label.getElement().getStyle().setCursor( POINTER );
			label.addClickHandler( new ClickHandler() {
				@Override
				public void onClick( ClickEvent event ) {

				}
			} );
			accordionGroup.add( label );
		}
	}

	/**
	 * Requests that the presenter reveal itself in its parent presenter. You can override this method to either fire a
	 * {@link com.gwtplatform.mvp.client.proxy.RevealContentEvent RevealContentEvent}, a
	 * {@link com.gwtplatform.mvp.client.proxy.RevealRootContentEvent RevealRootContentEvent} or a
	 * {@link com.gwtplatform.mvp.client.proxy.RevealRootLayoutContentEvent RevealRootLayoutContentEvent}.
	 */
	@Override
	protected void revealInParent() {
        RevealRootContentEvent.fire( this, this );
	}

	@ProxyCodeSplit
	@NameToken( NameTokens.main )
	@UseGatekeeper( LoggedInGatekeeper.class )
	public interface MainPageProxy extends Proxy<MainPagePresenter>, Place {
	}

	public interface MainPageView extends View, HasUiHandlers<MainPageUiHandlers> {

		void handleFeedGroups( Map<String, FeedGroup> result );
	}
}
