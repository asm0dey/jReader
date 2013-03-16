package com.github.asm0dey.client;

import com.github.asm0dey.client.services.HumanService;
import com.github.asm0dey.client.services.HumanServiceAsync;
import com.github.asm0dey.shared.domain.FeedGroup;
import com.github.asm0dey.shared.domain.Human;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootLayoutPanel;

import java.util.ArrayList;

/**
 * User: finkel
 * <p/>
 * Date: 16.03.13
 * <p/>
 * Time: 14:31
 */
public class Main implements EntryPoint {
	public void onModuleLoad() {
		Human human = new Human( "asm0dey@asm0dey.ru", "ss", true, 0, new ArrayList<FeedGroup>() );
        HumanServiceAsync.Util.getInstance().createUser(human,new AsyncCallback<Human>() {
            @Override
            public void onFailure(Throwable caught) {
                RootLayoutPanel.get().add( new Label( caught.getMessage() ) );
            }

            @Override
            public void onSuccess(Human result) {
                RootLayoutPanel.get().add( new Label( result.toString() ) );
            }
        });

	}
}
