package fr.jmini.slf4j.experiment.internal;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.jmini.slf4j.experiment.SomeService;

@Component(service = SomeService.class)
public class SomeServiceImpl implements SomeService {
    private static final Logger LOG = LoggerFactory.getLogger(SomeServiceImpl.class);

    @Override
    public String doIt(String first, String second) {
        LOG.info("SomeServiceImpl: doIt(..)");
        return operation(first, second);
    }

    static String operation(String first, String second) {
        return first + second;
    }

    @Activate
    public void activate() {
        LOG.info("SomeServiceImpl: activate");
    }

    @Deactivate
    public void deactivate() {
        LOG.info("SomeServiceImpl: deactivate");
    }
}
