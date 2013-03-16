package com.github.asm0dey.client.services;

import com.github.asm0dey.shared.domain.Human;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath( "springGwtServices/humanService" )
public interface HumanService extends RemoteService {
	public Human createUser( Human human );
}