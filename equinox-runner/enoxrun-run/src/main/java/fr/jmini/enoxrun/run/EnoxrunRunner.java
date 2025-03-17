package fr.jmini.enoxrun.run;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.osgi.launch.EquinoxFactory;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.launch.Framework;
import org.osgi.framework.launch.FrameworkFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.jmini.abundle.api.AService;
import fr.jmini.abundle.api.AServiceInput;

public class EnoxrunRunner {
    public static final String ABUNDLE_IMPL_BUNDLE_ID = "abundle.impl";

    private static final Logger LOG = LoggerFactory.getLogger(EnoxrunRunner.class);

    private Path workingDir;
    private Set<BundleHolder> bundles;
    private AServiceInput input;

    public EnoxrunRunner(Path workingDir, Set<BundleHolder> bundles, AServiceInput input) {
        this.workingDir = workingDir;
        this.bundles = bundles;
        this.input = input;
    }

    public void runEquinox() throws BundleException, InterruptedException {
        Path enoxrunDir = workingDir
                .resolve("enoxrun_" + System.currentTimeMillis());
        String workspaceDir = enoxrunDir.resolve("workspace")
                .toString();
        String storageDir = enoxrunDir.resolve("configuration")
                .toString();

        String exportPackage = findUCompilerApiExportPackage();

        Map<String, String> config = new HashMap<>();
        config.put("osgi.instance.area", workspaceDir);
        config.put(Constants.FRAMEWORK_STORAGE, storageDir);
        config.put(Constants.FRAMEWORK_STORAGE_CLEAN, Constants.FRAMEWORK_STORAGE_CLEAN_ONFIRSTINIT);
        config.put(Constants.FRAMEWORK_SYSTEMPACKAGES_EXTRA, exportPackage);

        FrameworkFactory frameworkFactory = new EquinoxFactory();
        Framework framework = frameworkFactory.newFramework(config);
        try {
            framework.init();
            framework.start();

            if (LOG.isDebugEnabled()) {
                LOG.debug("Available bundles: [{}]\n", bundles.stream()
                        .map(BundleHolder::getBundleSymbolicName)
                        .collect(java.util.stream.Collectors.joining(", ")));
            }

            BundleHolder implBundle = findBundle(ABUNDLE_IMPL_BUNDLE_ID);
            List<String> bundleList = EnoxrunUtils.readEntryInJar(implBundle.getJarFile(), "start-osgi.txt", EnoxrunRunner::toLines)
                    .orElseThrow(() -> new IllegalStateException("Not able to find resource 'start-osgi.txt' in 'abundle.impl' bundle"));

            BundleContext bundleContext = framework.getBundleContext();
            for (String symbolicName : bundleList) {
                startBundle(bundleContext, findBundle(symbolicName));
            }
            printState(bundleContext);

            ServiceReference<AService> serviceReference = bundleContext.getServiceReference(AService.class);
            AService service = bundleContext.getService(serviceReference);
            service.run(input);

        } finally {
            framework.stop();
            framework.waitForStop(0);
        }
    }

    private static List<String> toLines(InputStream in) {
        try (BufferedReader r = new BufferedReader(new InputStreamReader(in))) {
            return r.lines()
                    .map(String::trim)
                    .filter(s -> !s.startsWith("//"))
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new EnoxrunExecutionException("Could not read lines in 'start-osgi.txt'", e);
        }
    }

    private static void printState(BundleContext bundleContext) {
        LOG.debug("--- print state:");
        for (Bundle b : bundleContext.getBundles()) {
            printBundleState(b);
        }
    }

    private static void printBundleState(Bundle bundle) {
        LOG.debug("({}) {}: {} [{}]", bundle.getBundleId(), bundle.getSymbolicName(), bundle.getLocation(), toState(bundle.getState()));
    }

    private static String toState(int state) {
        switch (state) {
        case Bundle.UNINSTALLED:
            return "UNINSTALLED";
        case Bundle.INSTALLED:
            return "INSTALLED";
        case Bundle.RESOLVED:
            return "RESOLVED";
        case Bundle.STARTING:
            return "STARTING";
        case Bundle.STOPPING:
            return "STOPPING";
        case Bundle.ACTIVE:
            return "ACTIVE";
        default:
            return null;
        }
    }

    private String findUCompilerApiExportPackage() {
        BundleHolder apiBundle = findBundle("abundle-api");
        return apiBundle.getExportPackage();
    }

    private void startBundle(BundleContext bundleContext, BundleHolder bundleHolder) throws BundleException {
        URI file = bundleHolder.getJarFile()
                .toURI();
        LOG.debug("Starting bundle: {}", file);
        Bundle bundle = bundleContext.installBundle(file.toString());
        if (bundleHolder.getFragmentHost() == null) {
            bundle.start(Bundle.START_ACTIVATION_POLICY);
        }
    }

    private BundleHolder findBundle(String symbolicName) {
        return findBundle(bundles, symbolicName);
    }

    public static BundleHolder findBundle(Set<BundleHolder> allBundles, String symbolicName) {
        return allBundles.stream()
                .filter(b -> Objects.equals(symbolicName, b.getBundleSymbolicName()))
                .findAny()
                .orElseThrow(() -> new EnoxrunException("Could not find bundle with symbolic name '" + symbolicName + "' in bundle holder list"));
    }
}
