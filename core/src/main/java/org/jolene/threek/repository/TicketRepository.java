package org.jolene.threek.repository;

import org.jolene.threek.entity.Ticket;
import org.jolene.threek.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Jolene
 */
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    /**
     * 获取当前可用的入场券数量
     *
     * @param user 用户
     * @return 数量
     */
    int countByUsedFalseAndClaimant(User user);
}
