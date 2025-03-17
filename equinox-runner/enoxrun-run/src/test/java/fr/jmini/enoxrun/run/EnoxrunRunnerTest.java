package fr.jmini.enoxrun.run;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.osgi.framework.BundleException;

import fr.jmini.abundle.api.AServiceInput;

class EnoxrunRunnerTest {

    @Test
    void test() throws Exception {
        runTest();
    }

    private void runTest() throws URISyntaxException, IOException, BundleException, InterruptedException {
        Set<BundleHolder> bundles = new HashSet<>();
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        URL[] urls = ((URLClassLoader) classLoader).getURLs();
        for (URL url : urls) {
            Path file = Paths.get(url.toURI());
            if (Files.isRegularFile(file)) {
                System.out.println(file);
                Optional<BundleHolder> bundleHolder = EnoxrunUtils.toBundleHolder(file.toFile());
                if (bundleHolder.isPresent()) {
                    bundles.add(bundleHolder.get());
                }
            }
        }

        Path tmpDir = Files.createTempDirectory("fixture");

        Path workingDir = tmpDir.resolve("test");
        AServiceInput input = new AServiceInput();
        input.setProjectName("Fixture_TEST");
        input.setDebug(false);
        input.setWorkingDir(workingDir);

        new EnoxrunRunner(tmpDir, bundles, input).runEquinox();

        Path createdFile = workingDir.resolve("file.txt");
        assertThat(createdFile).exists();
        String content = read(createdFile);
        assertThat(content).startsWith("== Fixture_TEST\n");
    }

    private Path packageFolder(Path root, String... packageParts) {
        Path result = root;
        for (String part : packageParts) {
            result = result.resolve(part);
        }
        return result;
    }

    private static Set<File> readResource(String name) throws IOException {
        try (InputStream resource = EnoxrunRunnerTest.class.getResourceAsStream(name)) {
            return new BufferedReader(new InputStreamReader(resource,
                    StandardCharsets.UTF_8)).lines()
                            .map(File::new)
                            .collect(Collectors.toSet());
        }
    }

    private static String read(Path path) throws IOException {
        return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
    }

}
