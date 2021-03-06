package fr.jmini.bnd.ipsum.internal;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

@Component(immediate = true)
public class IpsumImmediateComponent {

    @Activate
    public void activate() {
        System.out.println("IpsumImmediateComponent: activate");
    }

    @Deactivate
    public void deactivate() {
        System.out.println("IpsumImmediateComponent: deactivate");
    }
}
