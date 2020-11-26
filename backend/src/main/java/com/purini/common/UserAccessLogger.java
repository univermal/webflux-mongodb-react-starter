package com.purini.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import reactor.netty.http.server.AccessLog;

public class UserAccessLogger {

    private static final Logger log = LoggerFactory.getLogger(AccessLog.class);

    public static void log(UsernamePasswordAuthenticationToken user) {
        if (log.isInfoEnabled()) {
            log.info("{} - ", user.getName());
        }
    }

}
