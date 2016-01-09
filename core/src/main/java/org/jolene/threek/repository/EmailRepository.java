package org.jolene.threek.repository;

import org.jolene.threek.entity.Email;
import org.jolene.threek.entity.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Jolene
 */
@Repository
public interface EmailRepository extends JpaRepository<Email, Long> {

    /**
     * 获取未读邮件
     *
     * @param login 用户
     * @return 队列
     */
    List<Email> findByBelongAndReadFalse(Login login);
}
