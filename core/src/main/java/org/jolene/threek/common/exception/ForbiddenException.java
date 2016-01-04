package org.jolene.threek.common.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

import java.nio.charset.Charset;

/**
 * 关联到的HTTP状态是403
 *
 * @author Jolene
 */
public class ForbiddenException extends HttpStatusCodeException {

    public ForbiddenException() {
        super(HttpStatus.FORBIDDEN);
    }

    public ForbiddenException(String statusText) {
        super(HttpStatus.FORBIDDEN, statusText);
    }

    public ForbiddenException(String statusText, byte[] responseBody, Charset responseCharset) {
        super(HttpStatus.FORBIDDEN, statusText, responseBody, responseCharset);
    }

    public ForbiddenException(String statusText, HttpHeaders responseHeaders, byte[] responseBody, Charset responseCharset) {
        super(HttpStatus.FORBIDDEN, statusText, responseHeaders, responseBody, responseCharset);
    }
}
