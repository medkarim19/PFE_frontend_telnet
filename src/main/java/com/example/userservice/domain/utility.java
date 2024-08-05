package com.example.userservice.domain;

import javax.servlet.http.HttpServletRequest;

public class utility {
    public static String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }
}
