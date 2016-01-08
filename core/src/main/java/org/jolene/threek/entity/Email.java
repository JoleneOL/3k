package org.jolene.threek.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * 邮件,特指收件
 *
 * @author Jolene
 */
@Entity
@Getter
@Setter
public class Email {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 拥有者,收件人
     */
    @ManyToOne(optional = false)
    private Login belong;
    private boolean flagged;
    private boolean trashed;
    @ManyToOne(optional = false)
    private EmailContent content;

}
