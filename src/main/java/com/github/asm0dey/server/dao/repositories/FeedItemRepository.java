package com.github.asm0dey.server.dao.repositories;

import com.github.asm0dey.shared.domain.FeedItem;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * User: finkel
 * <p/>
 * Date: 16.03.13
 * <p/>
 * Time: 17:52
 */
public interface FeedItemRepository extends JpaRepository<FeedItem, Long> {
}
