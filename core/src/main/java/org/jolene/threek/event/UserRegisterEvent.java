package org.jolene.threek.event;

import lombok.Data;
import org.jolene.threek.entity.User;

/**
 * 用户注册事件
 *
 * @author Jolene
 */
@Data
public class UserRegisterEvent {

    private final User user;

}
