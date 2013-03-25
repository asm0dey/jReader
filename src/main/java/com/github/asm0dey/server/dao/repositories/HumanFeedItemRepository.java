package com.github.asm0dey.server.dao.repositories;

import com.github.asm0dey.shared.domain.FeedItem;
import com.github.asm0dey.shared.domain.Human;
import com.github.asm0dey.shared.domain.HumanFeedItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * User: finkel
 * 
 * Date: 25.03.13
 * 
 * Time: 20:52
 */
@Repository
public interface HumanFeedItemRepository extends JpaRepository<HumanFeedItem, Long> {
	public HumanFeedItem findByHumanAndFeedItem( Human human, FeedItem feedItem );
}
