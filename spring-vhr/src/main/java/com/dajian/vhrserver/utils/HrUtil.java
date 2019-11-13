package com.dajian.vhrserver.utils;

import com.dajian.vhrserver.pojo.Hr;
import org.springframework.security.core.context.SecurityContextHolder;

public class HrUtil {
    public static Hr getCurrentHr(){
        Hr hr = (Hr)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return hr;
    }
}
