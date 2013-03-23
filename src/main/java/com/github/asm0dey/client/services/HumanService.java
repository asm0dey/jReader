package com.github.asm0dey.client.services;

import com.github.asm0dey.shared.domain.Exception.AuthenticationException;
import com.github.asm0dey.shared.domain.Exception.CreateUserException;
import com.github.asm0dey.shared.domain.FeedGroup;
import com.github.asm0dey.shared.domain.FeedItem;
import com.github.asm0dey.shared.domain.Human;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;
import java.util.Map;

@RemoteServiceRelativePath( "springGwtServices/humanService" )
public interface HumanService extends RemoteService {
	public Long createUser( Human human ) throws AuthenticationException, CreateUserException;
    public List<FeedItem> listAllFeedItems(Human human);
    public Map<String,Long> listUserFeedGroups(Long userId);
}