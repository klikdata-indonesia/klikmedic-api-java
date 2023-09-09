package io.github.klikdata.impl;

import io.github.klikdata.Klikmedic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KlikmedicClient implements Klikmedic {
    private static final Logger log = LoggerFactory.getLogger(KlikmedicClient.class);
    @Override
    public String sayHello() {
        log.info("Run sayHello()");
        return "Hello BPJS";
    }
}
