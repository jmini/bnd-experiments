package fr.jmini.enoxrun.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.osgi.test.common.annotation.InjectService;
import org.osgi.test.junit5.service.ServiceExtension;

import fr.jmini.abundle.api.AService;
import fr.jmini.abundle.api.AServiceInput;

@ExtendWith(ServiceExtension.class)
public class AServiceTest {

    @InjectService
    AService service;

    @Test
    public void test() throws Exception {
        assertThat(service)
                .as("service is null. This test must be run inside an OSGi framework")
                .isNotNull();

        Path workingDir = Files.createTempDirectory("test");
        AServiceInput input = new AServiceInput();
        input.setProjectName("Project name");
        input.setDebug(false);
        input.setWorkingDir(workingDir);
        service.run(input);

        assertThat(workingDir.resolve("file.txt")).hasContent("== Project name\n");
    }
}
