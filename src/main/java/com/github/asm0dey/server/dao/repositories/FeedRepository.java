package com.github.asm0dey.server.dao.repositories;

import com.github.asm0dey.shared.domain.Feed;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * User: finkel
 * <p/>
 * Date: 16.03.13
 * <p/>
 * Time: 17:38
 */
public interface FeedRepository extends JpaRepository<Feed, Long> {
}
