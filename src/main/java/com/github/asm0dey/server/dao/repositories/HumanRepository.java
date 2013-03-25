package com.github.asm0dey.server.dao.repositories;

import com.github.asm0dey.shared.domain.FeedGroup;
import com.github.asm0dey.shared.domain.FeedItem;
import com.github.asm0dey.shared.domain.Human;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * User: finkel
 * <p/>
 * Date: 16.03.13
 * <p/>
 * Time: 13:58
 */
@Repository
public interface HumanRepository extends JpaRepository<Human, Long> {
	@Query( "select item from Human as human inner join human.categories as category inner join category.feeds as feed inner join feed.items as item" )
	public List<FeedItem> listAllItems( Pageable pageable );

	@Query( "select i from FeedItem i where i.feed.id=?1 and i not in (select hfi.feedItem from HumanFeedItem hfi where human.id=?2 and isRead=true )" )
	public List<FeedItem> listUnreadItems(Long feedId, Long humanId, Pageable pageable);
    public Human findByEmail(String email);
    @Query("select human.categories from Human human where human.id=?1")
    public Map<String,FeedGroup> listFeedGroups(Long id);
}
