package fr.jmini.abundle.impl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.osgi.service.component.annotations.Component;

import fr.jmini.abundle.api.AService;
import fr.jmini.abundle.api.AServiceInput;

/**
 * This is an implementation of 'AService'.
 */
@Component(service = AService.class)
public class AServiceImpl implements AService {

    @Override
    public void run(AServiceInput input) {
        Path file = input.getWorkingDir()
                .resolve("file.txt");

        StringBuilder sb = new StringBuilder();
        sb.append("== " + input.getProjectName() + "\n");
        if (input.isDebug()) {
            sb.append(System.currentTimeMillis());
            sb.append("\n");
        }

        try {
            writeFile(file, sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeFile(Path file, String content) throws IOException {
        Files.createDirectories(file.getParent());
        Files.write(file, content.getBytes(StandardCharsets.UTF_8));
    }
}
