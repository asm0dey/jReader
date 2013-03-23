package com.github.asm0dey.client;

import com.github.asm0dey.client.presenters.LoginPagePresenter;
import com.github.asm0dey.client.presenters.MainPagePresenter;
import com.github.asm0dey.client.views.LoginPageViewImpl;
import com.github.asm0dey.client.views.MainPageViewImpl;
import com.gwtplatform.dispatch.client.gin.DispatchAsyncModule;
import com.gwtplatform.mvp.client.annotations.DefaultPlace;
import com.gwtplatform.mvp.client.annotations.ErrorPlace;
import com.gwtplatform.mvp.client.annotations.UnauthorizedPlace;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.gin.DefaultModule;

/**
 * User: finkel
 * <p/>
 * Date: 19.03.13
 * <p/>
 * Time: 23:37
 */
public class ClientModule extends AbstractPresenterModule {

	@Override
	protected void configure() {
		install( new DefaultModule() );
		install( new DispatchAsyncModule.Builder().build() );
// install(new ApplMo);
		bindConstant().annotatedWith( DefaultPlace.class ).to( NameTokens.home );
		bindConstant().annotatedWith( ErrorPlace.class ).to( NameTokens.home );
		bindConstant().annotatedWith( UnauthorizedPlace.class ).to( NameTokens.home );

		bindPresenter( LoginPagePresenter.class, LoginPagePresenter.LoginPageView.class, LoginPageViewImpl.class,
				LoginPagePresenter.LoginPageProxy.class );
		bindPresenter( MainPagePresenter.class, MainPagePresenter.MainPageView.class, MainPageViewImpl.class, MainPagePresenter.MainPageProxy.class );

	}
}
