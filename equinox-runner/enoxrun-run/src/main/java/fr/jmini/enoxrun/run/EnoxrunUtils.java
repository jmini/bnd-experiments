package fr.jmini.enoxrun.run;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Optional;
import java.util.function.Function;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import org.osgi.framework.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnoxrunUtils {
    private static final String MANIFEST_FILE_KEY = "META-INF/MANIFEST.MF";
    private static final Logger LOG = LoggerFactory.getLogger(EnoxrunUtils.class);

    public static <T> Optional<T> readEntryInJar(File file, String name, Function<InputStream, T> reader) {
        try (JarFile jar = new JarFile(file)) {
            Enumeration<JarEntry> entries = jar.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                if (entry.getName()
                        .equals(name)) {
                    return Optional.of(reader.apply(jar.getInputStream(entry)));
                }
            }
        } catch (IOException ex) {
            throw new EnoxrunExecutionException("Error while reading the jar '" + file + "' to find entry '" + name + "'", ex);
        }
        LOG.info("Could not find entry '{}' in file '{}'", name, file);
        return Optional.empty();
    }

    public static Optional<BundleHolder> toBundleHolder(File file) {
        return readEntryInJar(file, MANIFEST_FILE_KEY, is -> readManifest(file, is));
    }

    private static BundleHolder readManifest(File file, InputStream is) {
        try {
            Manifest manifest = new Manifest(is);
            Attributes attributes = manifest.getMainAttributes();
            String bundleSymbolicName = attributes.getValue(Constants.BUNDLE_SYMBOLICNAME);
            if (bundleSymbolicName != null) {
                int i = bundleSymbolicName.indexOf(";");
                if (i > -1) {
                    bundleSymbolicName = bundleSymbolicName.substring(0, i);
                }
            }
            String bundleVersion = attributes.getValue(Constants.BUNDLE_VERSION);
            String exportPackage = attributes.getValue(Constants.EXPORT_PACKAGE);
            String fragmentHost = attributes.getValue(Constants.FRAGMENT_HOST);
            return new BundleHolder(file, bundleSymbolicName, bundleVersion, exportPackage, fragmentHost);
        } catch (IOException ex) {
            throw new EnoxrunExecutionException("Error while reading the MANIFEST.MF entry in " + file, ex);
        }
    }

    private EnoxrunUtils() {
    }
}
