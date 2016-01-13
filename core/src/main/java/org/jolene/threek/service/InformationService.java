package org.jolene.threek.service;

import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * 信息服务
 *
 * @author Jolene
 */
@Service
public class InformationService {

    /**
     * 马赛克下手机号码
     *
     * @param mobile 必须是手机号码
     * @return 马赛克以后的手机号码
     */
    public String mosaic(String mobile) {
        char[] chars = mobile.toCharArray();
        int start = chars.length * 3 / 11;
        int end = chars.length * 4 / 11;
        // 0..start..end..-1
        end = chars.length - end;
        char[] newChars = new char[chars.length];
        Arrays.fill(newChars, '*');
        System.arraycopy(chars, 0, newChars, 0, start);
        System.arraycopy(chars, end, newChars, end, chars.length - end);
        return new String(newChars);
    }

}
