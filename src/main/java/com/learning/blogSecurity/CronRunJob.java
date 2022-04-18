package com.learning.blogSecurity;

import jdk.internal.instrumentation.Logger;
import org.slf4j.LoggerFactory;

public class CronRunJob {
    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(CronRunJob.class);

    public void run(String... args) throws Exception {
        LOGGER.info("Hello, World!");
    }
}
