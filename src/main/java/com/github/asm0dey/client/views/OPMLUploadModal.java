package com.github.asm0dey.client.views;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.Modal;
import com.github.gwtbootstrap.client.ui.event.HideHandler;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Image;
import org.moxieapps.gwt.uploader.client.Uploader;
import org.moxieapps.gwt.uploader.client.events.FileDialogCompleteEvent;
import org.moxieapps.gwt.uploader.client.events.FileDialogCompleteHandler;
import org.moxieapps.gwt.uploader.client.events.UploadSuccessEvent;
import org.moxieapps.gwt.uploader.client.events.UploadSuccessHandler;

/**
 * User: finkel
 * <p/>
 * Date: 26.03.13
 * <p/>
 * Time: 10:14
 */
public class OPMLUploadModal extends Modal {

	private static OPMLUploadModalUiBinder ourUiBinder = GWT.create( OPMLUploadModalUiBinder.class );
	private final Modal rootElement;
	@UiField
	Button cancelButton;
/*
	@UiField
	Button submitButton;
*/
	@UiField
	Image loader;
	@UiField( provided = true )
	Uploader uploader = new Uploader();
	private String results;

	public OPMLUploadModal() {
		rootElement = ourUiBinder.createAndBindUi( this );
        uploader.setUploadURL( "/gwtapp/upload" ).setButtonText( "Select OPML file to import" )
				.setFileDialogCompleteHandler( new FileDialogCompleteHandler() {
					@Override
					public boolean onFileDialogComplete( FileDialogCompleteEvent fileDialogCompleteEvent ) {
//						submitButton.setEnabled( false );
						cancelButton.setEnabled( false );
						uploader.setVisible( false );
						loader.setVisible( true );
                        uploader.startUpload();
						return fileDialogCompleteEvent.getNumberOfFilesSelected() > 0;
					}
				} ).setUploadSuccessHandler( new UploadSuccessHandler() {
					@Override
					public boolean onUploadSuccess( UploadSuccessEvent uploadSuccessEvent ) {
						String serverData = uploadSuccessEvent.getServerData();
						setResults( serverData );
						hide();
						return true;
					}
				} );
		uploader.setStyleName( new Button().getStyleName() );
	}

	@UiHandler( { /*"submitButton",*/ "cancelButton" } )
	public void handleClick( ClickEvent event ) {
		Object source = event.getSource();
		if ( source instanceof Button ) {
			Button button = (Button) source;
			if ( button == cancelButton ) {
				setResults( null );
				hide();
			} else {
				if ( uploader.isVisible() )
					uploader.startUpload();
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void hide() {
		rootElement.hide();
	}

	/**
	 * {@inheritDoc}
	 */
	public void show() {
		rootElement.show();
	}

	/**
	 * {@inheritDoc}
	 */
	public void toggle() {
		rootElement.toggle();
	}

	public String getResults() {
		return results;
	}

	public void setResults( String results ) {
		this.results = results;
	}

	/**
	 * {@inheritDoc}
	 */
	public HandlerRegistration addHideHandler( HideHandler handler ) {
		return rootElement.addHideHandler( handler );
	}

	interface OPMLUploadModalUiBinder extends UiBinder<Modal, OPMLUploadModal> {
	}

}