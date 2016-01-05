package org.jolene.threek.service;

import org.jolene.threek.entity.Login;
import org.springframework.transaction.annotation.Transactional;

/**
 * 登录相关的服务
 *
 * @author Jolene
 */
public interface LoginService {

    /**
     * 修改某一个可登录身份的密码
     *
     * @param login       可登录身份
     * @param rawPassword 明文密码
     * @return 新的可登录身份
     */
    @Transactional
    <T extends Login> T changeLoginWithRawPassword(T login, String rawPassword);
}
