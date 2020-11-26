
package reactor.netty.http.server;

import reactor.util.Logger;
import reactor.util.Loggers;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

/**
 * Currently the access log implementation of netty does not support logging user name.
 * This is a temporary override class which adds an explicit \n in the log format.
 * So, it allows the user name to be logged from somewhere else in the same line using the same logger.
 */

public final class AccessLog {
    private static final Logger log = Loggers.getLogger(AccessLog.class);
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z", Locale.US);
    private static final String COMMON_LOG_FORMAT = "{} - [{}] \"{} {} {}\" {} {} {} {} ms\n";

    private final String zonedDateTime;

    private String address;
    private CharSequence method;
    private CharSequence uri;
    private String protocol;
    private String user;
    private CharSequence status;
    private long contentLength;
    private boolean chunked;
    private long startTime = System.currentTimeMillis();
    private int port;

    public AccessLog() {
        this.zonedDateTime = ZonedDateTime.now().format(DATE_TIME_FORMATTER);
    }

    public AccessLog address(String address) {
        this.address = Objects.requireNonNull(address, "address");
        return this;
    }

    public AccessLog port(int port) {
        this.port = port;
        return this;
    }

    public AccessLog method(CharSequence method) {
        this.method = Objects.requireNonNull(method, "method");
        return this;
    }

    public AccessLog uri(CharSequence uri) {
        this.uri = Objects.requireNonNull(uri, "uri");
        return this;
    }

    public AccessLog protocol(String protocol) {
        this.protocol = Objects.requireNonNull(protocol, "protocol");
        return this;
    }

    public AccessLog status(CharSequence status) {
        this.status = Objects.requireNonNull(status, "status");
        return this;
    }

    public AccessLog contentLength(long contentLength) {
        this.contentLength = contentLength;
        return this;
    }

    public AccessLog increaseContentLength(long contentLength) {
        if (chunked) {
            this.contentLength += contentLength;
        }
        return this;
    }

    public AccessLog chunked(boolean chunked) {
        this.chunked = chunked;
        return this;
    }

    public AccessLog user(String user) {
        this.user = user;
        return this;
    }

    private long duration() {
        return System.currentTimeMillis() - startTime;
    }

    public void log() {
        if (log.isInfoEnabled()) {
            log.info(COMMON_LOG_FORMAT, address, zonedDateTime,
                    method, uri, protocol, status, (contentLength > -1 ? contentLength : "-"), port, duration());
        }
    }
}
