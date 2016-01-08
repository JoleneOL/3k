package org.jolene.threek.service;

import org.jolene.threek.entity.Ticket;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.function.Consumer;

/**
 * @author Jolene
 */
public interface TicketService {

    /**
     * 创建好多入场券
     *
     * @param number   数量
     * @param consumer 每个入场卷都将被此消耗
     * @return 新入场券的集合
     */
    @Transactional
    Collection<Ticket> newTickets(int number, Consumer<Ticket> consumer);
}
