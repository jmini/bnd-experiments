package fr.jmini.bnd.ipsum.internal;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

import fr.jmini.bnd.ipsum.IpsumService;

@Component(service = IpsumService.class)
public class IpsumComponent implements IpsumService {

    @Override
    public String doIt(String first, String second) {
        System.out.println("IpsumComponent: doIt(..)");
        return operation(first, second);
    }

    static String operation(String first, String second) {
        return first + second;
    }

    @Activate
    public void activate() {
        System.out.println("IpsumComponent: activate");
    }

    @Deactivate
    public void deactivate() {
        System.out.println("IpsumComponent: deactivate");
    }
}
