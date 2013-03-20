package com.github.asm0dey.client;

import com.github.asm0dey.client.presenters.LoginPagePresenter;
import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.PasswordTextBox;
import com.github.gwtbootstrap.client.ui.TextBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.gwtplatform.mvp.client.ViewImpl;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

/**
 * User: finkel
 * <p/>
 * Date: 19.03.13
 * <p/>
 * Time: 23:52
 */
public class LoginPageViewImpl extends ViewWithUiHandlers<LoginPageUiHandlers> implements LoginPagePresenter.LoginPageView {
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