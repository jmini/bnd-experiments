package fr.jmini.bnd.dolor;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

import fr.jmini.bnd.dolor.api.DolorService;

@Component(service = DolorService.class)
public class DolorComponent implements DolorService {

    @Override
    public String doIt(String first, String second) {
        System.out.println("DolorComponent: doIt(..)");
        return first + second;
    }

    @Activate
    public void activate() {
        System.out.println("DolorComponent: activate");
    }

    @Deactivate
    public void deactivate() {
        System.out.println("DolorComponent: deactivate");
    }
}
