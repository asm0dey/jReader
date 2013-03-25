package com.github.asm0dey.server.dao.repositories;

import com.github.asm0dey.shared.domain.Feed;
import com.github.asm0dey.shared.domain.FeedItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * User: finkel
 * <p/>
 * Date: 16.03.13
 * <p/>
 * Time: 17:52
 */
@Repository
public interface FeedItemRepository extends JpaRepository<FeedItem, Long> {
    public List<FeedItem> findByFeed_IdOrderByCreatedOnDesc(Long feed_Id,Pageable pageable);

}
