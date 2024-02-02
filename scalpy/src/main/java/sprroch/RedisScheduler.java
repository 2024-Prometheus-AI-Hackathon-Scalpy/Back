package sprroch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisScheduler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private void log(String name) {
        logger.trace("trace log={}", name);
        logger.debug("debug log={}", name);
        logger.info(" info log={}", name);
        logger.warn(" warn log={}", name);
        logger.error("error log={}", name);
    }
}
