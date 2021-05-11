package fr.jmini.bnd.dolor;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

@Component(immediate = true)
public class DolorImmediateComponent {

    @Activate
    public void activate() {
        System.out.println("DolorImmediateComponent: activate");
    }

    @Deactivate
    public void deactivate() {
        System.out.println("DolorImmediateComponent: deactivate");
    }
}
