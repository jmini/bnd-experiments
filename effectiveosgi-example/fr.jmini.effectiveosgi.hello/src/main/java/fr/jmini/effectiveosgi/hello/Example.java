package fr.jmini.effectiveosgi.hello;

import org.osgi.service.component.annotations.*;

@Component
public class Example {

    @Activate
    void activate() {
        System.out.println("Hello World");
    }
}
