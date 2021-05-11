package fr.jmini.bnd.lorem;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

    @Override
    public void start(BundleContext context) throws Exception {
        System.out.println("Lorem Activator: start(..)");
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        System.out.println("Lorem Activator: stop(..)");
    }
}
