## pde-run

This is a small project to reproduce an issue I have when I try to run some tests using bnd for a project that has a dependency to `org.eclipse.pde.core`.

This is the error I get when I run `./gradlew build`:

```
Resolution failed. Capabilities satisfying the following requirements could not be found:
    [<<INITIAL>>]
      ⇒ osgi.identity: (osgi.identity=org.eclipse.pde.core)
          ⇒ [org.eclipse.pde.core version=3.13.200.v20191202-2135]
              ⇒ osgi.wiring.bundle: (&(osgi.wiring.bundle=org.eclipse.team.core)(bundle-version>=3.2.0)(!(bundle-version>=4.0.0)))
                  ⇒ [org.eclipse.team.core version=3.8.800.v20191122-2107]
                      ⇒ osgi.wiring.bundle: (&(osgi.wiring.bundle=org.eclipse.compare.core)(bundle-version>=3.5.200)(!(bundle-version>=4.0.0)))
                          ⇒ [org.eclipse.compare.core version=3.6.700.v20191122-2107]
                              ⇒ osgi.wiring.package: (&(osgi.wiring.package=com.ibm.icu.text)(version>=3.6.1))
```

Question on stackoverflow: [Can not run OSGi tests requiring "org.eclipse.pde.core" because of icu4j](https://stackoverflow.com/q/70168415)