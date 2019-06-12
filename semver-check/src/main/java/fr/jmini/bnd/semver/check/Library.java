package fr.jmini.bnd.semver.check;

import fr.jmini.bnd.semver.check.internal.LibraryImpl;

public class Library {
    LibraryImpl delegate = new LibraryImpl();

    public boolean someLibraryMethod() {
        return delegate.someLibraryMethodImpl();
    }
}
