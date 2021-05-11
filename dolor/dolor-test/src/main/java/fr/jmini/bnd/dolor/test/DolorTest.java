package fr.jmini.bnd.dolor.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.osgi.test.common.annotation.InjectService;
import org.osgi.test.junit5.service.ServiceExtension;

import fr.jmini.bnd.dolor.api.DolorService;

@ExtendWith(ServiceExtension.class)
public class DolorTest {

    @InjectService
    DolorService service;

    @Test
    public void test() throws Exception {
        assertNotNull(service, "service is null. This test must be run inside an OSGi framework");

        String result = service.doIt("foo", "bar");
        assertEquals("foobar", result);
    }
}
