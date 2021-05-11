package fr.jmini.bnd.ipsum.internal;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class IpsumComponentTest {

    @Test
    public void test() throws Exception {
        String result = IpsumComponent.operation("bar", "foo");
        assertEquals("barfoo", result);
    }
}
