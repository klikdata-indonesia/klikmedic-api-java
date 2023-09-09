package io.github.klikdata;

import io.github.klikdata.impl.KlikmedicClient;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

class KlikmedicTest {
    private static final Logger log = LoggerFactory.getLogger(KlikmedicTest.class);

    @Test
    public void kmSayHelloTests() {
        KlikmedicClient km = new KlikmedicClient();
        log.debug(km.sayHello());
        assertEquals("Hello BPJS", km.sayHello());
    }

}