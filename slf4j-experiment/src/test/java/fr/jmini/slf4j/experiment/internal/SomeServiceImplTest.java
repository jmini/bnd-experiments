package fr.jmini.slf4j.experiment.internal;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SomeServiceImplTest {

    @Test
    public void test() throws Exception {
        String result = SomeServiceImpl.operation("bar", "foo");
        assertEquals("barfoo", result);
    }
}
