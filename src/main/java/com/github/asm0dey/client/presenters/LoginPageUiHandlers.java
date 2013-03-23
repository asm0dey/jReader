package com.github.asm0dey.client.presenters;

import com.gwtplatform.mvp.client.UiHandlers;

/**
 * User: finkel
 * <p/>
 * Date: 20.03.13
 * <p/>
 * Time: 0:40
 */
public interface LoginPageUiHandlers extends UiHandlers{
    void onSignUpIn(String email, String password);
}
