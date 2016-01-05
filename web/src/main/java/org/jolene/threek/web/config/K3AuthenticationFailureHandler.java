package org.jolene.threek.web.config;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 登录失败的可能
 * <ul>
 * <li>{@link BadCredentialsException}用户或者密码错误</li>
 * <li>{@link UsernameNotFoundException}用户未找到</li>
 * <li>{@link DisabledException}用户被禁用</li>
 * <li>{@link LockedException}用户被锁定</li>
 * <li>其他错误</li>
 * </ul>
 *
 * @author Jolene
 */
public class K3AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    public static final String AUTHENTICATION_MESSAGE = "AUTHENTICATION_MESSAGE";

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response
            , AuthenticationException exception) throws IOException, ServletException {

        HttpSession session = request.getSession(false);
        String message;
        if (exception instanceof BadCredentialsException) {
            message = "用户或者密码错误";
        } else if (exception instanceof UsernameNotFoundException) {
            message = "用户未找到";
        } else if (exception instanceof DisabledException) {
            message = "该用户已经被禁用";
        } else if (exception instanceof LockedException) {
            message = "该用户正在被锁定";
            // TODO 这种情况可能会提供一种向客服申诉的页面
        } else {
            message = "其他错误:" + exception.getLocalizedMessage();
        }
        session.setAttribute(AUTHENTICATION_MESSAGE, message);
        setDefaultFailureUrl("/login?type=error");

        super.onAuthenticationFailure(request, response, exception);
    }


}
