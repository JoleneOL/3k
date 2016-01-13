package org.jolene.threek.service.impl;

import org.jolene.threek.Utils;
import org.jolene.threek.entity.Login;
import org.jolene.threek.entity.User;
import org.jolene.threek.repository.LoginRepository;
import org.jolene.threek.service.LoginService;
import org.jolene.threek.service.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Jolene
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginRepository loginRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TimeService timeService;

    @Override
    @Transactional
    public <T extends Login> T changeLoginWithRawPassword(T login, String rawPassword) {
        login.setPassword(passwordEncoder.encode(rawPassword));
        if (login instanceof User) {
            if (((User) login).getCode() == null)
                ((User) login).setCode(Utils.randomString());
        }
        if (login.getLogoPath() == null)
            login.setLogoPath(User.DEFAULT_LOGO_PATH);
        if (login.getCreatedTime() == null) {
            login.setCreatedTime(timeService.nowDateTime());
        }
        return loginRepository.save(login);
    }
}
