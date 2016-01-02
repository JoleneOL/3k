package org.jolene.threek.repository;

import org.jolene.threek.entity.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Jolene
 */
@Repository
public interface LoginRepository extends JpaRepository<Login, Long> {

    /**
     * 根据用户名获取用户
     *
     * @param username 用户名
     * @return 唯一用户或者null
     */
    Login findByUsername(String username);
}
