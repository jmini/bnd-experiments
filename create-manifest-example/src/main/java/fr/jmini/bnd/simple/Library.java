package fr.jmini.bnd.simple;

import fr.jmini.bnd.simple.internal.LibraryImpl;

public class Library {
    LibraryImpl delegate = new LibraryImpl();

    public boolean someLibraryMethod() {
        return delegate.someLibraryMethodImpl();
    }
}
