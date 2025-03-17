package fr.jmini.abundle.api;

import java.nio.file.Path;

/**
 * The input passed to OSGi Service.
 */
public class AServiceInput {

    /**
     * The name
     */
    private String name;

    /**
     * Directory where the file is created
     */
    private Path workingDir;

    /**
     * Whether the debug is enabled or not
     */
    private boolean debug;

    public String getProjectName() {
        return name;
    }

    public void setProjectName(String projectName) {
        this.name = projectName;
    }

    public Path getWorkingDir() {
        return workingDir;
    }

    public void setWorkingDir(Path workingDir) {
        this.workingDir = workingDir;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    @Override
    public String toString() {
        return "AServiceInput [name=" + name + ", workingDir=" + workingDir + ", debug=" + debug + "]";
    }
}
