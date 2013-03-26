package com.github.asm0dey.client.presenters;

import com.github.asm0dey.client.services.FeedServiceAsync;
import com.github.asm0dey.shared.domain.FeedItem;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

import java.util.Date;

/**
 * User: finkel
 * <p/>
 * Date: 25.03.13
 * <p/>
 * Time: 15:26
 */
public class FeedItemPresenter extends PresenterWidget<FeedItemPresenter.FeedItemView> implements FeedItemUiHandlers {
	private FeedItem source;
	private Long uid;

	/**
	 * Creates a {@link com.gwtplatform.mvp.client.PresenterWidget} that uses automatic binding. This will only work when instantiating this object
	 * using Guice/GIN dependency injection. See {@link com.gwtplatform.mvp.client.HandlerContainerImpl#HandlerContainerImpl()} for more details on
	 * automatic binding.
	 * 
	 * @param eventBus
	 *            The {@link com.google.web.bindery.event.shared.EventBus}.
	 * @param view
	 *            The {@link com.gwtplatform.mvp.client.View}.
	 */
	@Inject
	public FeedItemPresenter( EventBus eventBus, FeedItemView view ) {
		super( eventBus, view );
		getView().setUiHandlers( this );
	}

	public void setSource( FeedItem source ) {
		this.source = source;
	}

	@Override
	public void markRead() {
		FeedServiceAsync.Util.getInstance().setItemRead( uid, source.getId(), new AsyncCallback<Void>() {
			@Override
			public void onFailure( Throwable caught ) {
			}

			@Override
			public void onSuccess( Void result ) {
			}
		} );
	}

	public void setUid( Long uid ) {
		this.uid = uid;
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
		getView().setHeader( source.getTitle() );
		getView().setDate( source.getCreatedOn() );
		getView().setText( source.getText() );
		getView().setLink( source.getUrl() );
		getView().setAuthor( source.getAuthor() );
		super.onReveal();
	}

	public interface FeedItemView extends View, HasUiHandlers<FeedItemUiHandlers> {
		void setHeader( String title );

		void setDate( Date createdOn );

		void setText( String text );

		void setLink( String url );

		void setAuthor( String author );
	}
}
