package com.github.asm0dey.client.presenters;


import com.github.asm0dey.client.LoginPageUiHandlers;
import com.github.asm0dey.client.NameTokens;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.Place;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealRootLayoutContentEvent;

/**
 * User: finkel
 * <p/>
 * Date: 19.03.13
 * <p/>
 * Time: 22:35
 */
public class LoginPagePresenter extends Presenter<LoginPagePresenter.LoginPageView,LoginPagePresenter.LoginPageProxy> implements LoginPageUiHandlers{


    /**
     * Creates a {@link com.gwtplatform.mvp.client.Presenter} that uses automatic binding. This will
     * only work when instantiating this object using Guice/GIN dependency injection.
     * See {@link com.gwtplatform.mvp.client.HandlerContainerImpl#HandlerContainerImpl()} for more details on
     * automatic binding.
     *
     * @param eventBus   The {@link com.google.web.bindery.event.shared.EventBus}.
     * @param view       The {@link com.gwtplatform.mvp.client.View}.
     * @param proxy      The {@link com.gwtplatform.mvp.client.proxy.Proxy}.
     */
    @Inject
    public LoginPagePresenter(EventBus eventBus, LoginPageView view, LoginPageProxy proxy) {
        super(eventBus, view, proxy);
        getView().setUiHandlers(this);
    }

    @Override
    public void onSignUpIn(String email, String password) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @ProxyStandard
    @NameToken(NameTokens.home)
    public interface LoginPageProxy extends Proxy<LoginPagePresenter>,Place {}
    public interface LoginPageView extends View,HasUiHandlers<LoginPageUiHandlers> {}

    /**
     * Requests that the presenter reveal itself in its parent presenter.
     * You can override this method to either fire a
     * {@link com.gwtplatform.mvp.client.proxy.RevealContentEvent RevealContentEvent},
     * a {@link com.gwtplatform.mvp.client.proxy.RevealRootContentEvent RevealRootContentEvent}
     * or a {@link com.gwtplatform.mvp.client.proxy.RevealRootLayoutContentEvent RevealRootLayoutContentEvent}.
     */
    @Override
    protected void revealInParent() {
        RevealRootLayoutContentEvent.fire(this,this);
    }
}
