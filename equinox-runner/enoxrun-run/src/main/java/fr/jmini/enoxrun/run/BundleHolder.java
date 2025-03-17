package fr.jmini.enoxrun.run;

import java.io.File;

public class BundleHolder {
    private File jarFile;
    private String bundleSymbolicName;
    private String bundleVersion;
    private String exportPackage;
    private String fragmentHost;

    public BundleHolder(File jarFile, String bundleSymbolicName, String bundleVersion, String exportPackage, String fragmentHost) {
        this.jarFile = jarFile;
        this.bundleSymbolicName = bundleSymbolicName;
        this.bundleVersion = bundleVersion;
        this.exportPackage = exportPackage;
        this.fragmentHost = fragmentHost;
    }

    public File getJarFile() {
        return jarFile;
    }

    public String getBundleSymbolicName() {
        return bundleSymbolicName;
    }

    public String getBundleVersion() {
        return bundleVersion;
    }

    public String getExportPackage() {
        return exportPackage;
    }

    public String getFragmentHost() {
        return fragmentHost;
    }

    @Override
    public String toString() {
        return "BundleHolder [jarFile=" + jarFile + ", bundleSymbolicName=" + bundleSymbolicName + ", bundleVersion=" + bundleVersion + ", exportPackage=" + exportPackage + ", fragmentHost=" + fragmentHost + "]";
    }
}