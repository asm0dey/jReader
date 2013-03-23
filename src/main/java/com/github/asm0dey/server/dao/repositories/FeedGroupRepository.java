package com.github.asm0dey.server.dao.repositories;

import com.github.asm0dey.shared.domain.FeedGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * User: finkel
 * <p/>
 * Date: 22.03.13
 * <p/>
 * Time: 15:37
 */
@Repository
public interface FeedGroupRepository extends JpaRepository<FeedGroup,Long> {
}
