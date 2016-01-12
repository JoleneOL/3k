package org.jolene.threek.web.model;

import lombok.Data;

/**
 * 注册
 *
 * @author Jolene
 */
@Data
public class RegisterInfo {
    private String code;
    private String mobile;
    private String password, password2;
}
