package com.github.asm0dey.server.dao.repositories;

import com.github.asm0dey.shared.domain.Human;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * User: finkel
 * <p/>
 * Date: 16.03.13
 * <p/>
 * Time: 13:58
 */
public interface HumanRepository extends JpaRepository<Human, Long> {
}
