package org.jolene.threek.repository;

import org.jolene.threek.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Jolene
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * @param user 上级用户
     * @return 列表, 输入用户的直接下线用户并且未被查看过.
     */
    List<User> findByGuideAndGuideKnowMeFalse(User user);

    /**
     * 根据邀请码获取指定用户
     *
     * @param code 邀请码
     * @return 用户或者null
     */
    User findByCode(String code);
}
