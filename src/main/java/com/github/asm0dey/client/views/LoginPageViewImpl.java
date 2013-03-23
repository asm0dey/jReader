package com.github.asm0dey.client.views;

import com.github.asm0dey.client.presenters.LoginPageUiHandlers;
import com.github.asm0dey.client.presenters.LoginPagePresenter;
import com.github.asm0dey.shared.domain.FeedItem;
import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.PasswordTextBox;
import com.github.gwtbootstrap.client.ui.TextBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import java.util.List;

/**
 * User: finkel
 * <p/>
 * Date: 19.03.13
 * <p/>
 * Time: 23:52
 */
public class LoginPageViewImpl extends ViewWithUiHandlers<LoginPageUiHandlers> implements LoginPagePresenter.LoginPageView {
    @Override
    public void showItems(List<FeedItem> result) {
        System.out.println("result = " + result);
    }

    interface LoginPageViewImplUiBinder extends UiBinder<HTMLPanel, LoginPageViewImpl> {
    }

    private static LoginPageViewImplUiBinder ourUiBinder = GWT.create(LoginPageViewImplUiBinder.class);
    @UiField
    TextBox emailField;
    @UiField
    Button signUpIn;
    @UiField
    PasswordTextBox passwordField;

    public LoginPageViewImpl() {
        HTMLPanel rootElement = ourUiBinder.createAndBindUi(this);
        initWidget(rootElement);
    }

    @UiHandler("signUpIn")
    public void handleClick(ClickEvent event) {
        getUiHandlers().onSignUpIn(emailField.getValue(),passwordField.getValue());
    }
}