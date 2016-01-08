package org.jolene.threek.service.impl;

import org.jolene.threek.Utils;
import org.jolene.threek.entity.Ticket;
import org.jolene.threek.repository.TicketRepository;
import org.jolene.threek.service.TicketService;
import org.jolene.threek.service.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

/**
 * @author Jolene
 */
@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TimeService timeService;
    @Autowired
    private TicketRepository ticketRepository;

    @Override
    @Transactional
    public Collection<Ticket> newTickets(int number, Consumer<Ticket> consumer) {
        ArrayList<Ticket> arrayList = new ArrayList<>();
        while (number-- > 0) {
            Ticket ticket = new Ticket();
            ticket.setId(Utils.randomString());
            ticket.setCreatedTime(timeService.nowDateTime());
            consumer.accept(ticket);
            arrayList.add(ticket);
        }
        return ticketRepository.save(arrayList);
    }
}
