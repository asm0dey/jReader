package com.github.asm0dey.server.dao.repositories;

import com.github.asm0dey.shared.domain.Feed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * User: finkel
 * <p/>
 * Date: 16.03.13
 * <p/>
 * Time: 17:38
 */
@Repository
public interface FeedRepository extends JpaRepository<Feed, Long> {
    public Feed findByUrl(String url);
}
