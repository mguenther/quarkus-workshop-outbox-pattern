package net.mguenther.reactive.employee;

import io.smallrye.common.annotation.Blocking;
import net.mguenther.reactive.adapter.solr.SolrAdapter;
import org.apache.solr.common.SolrInputDocument;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class EmployeeIndexer {

    private static final Logger LOG = Logger.getLogger(EmployeeIndexer.class);

    private final SolrAdapter solr;

    @Inject
    public EmployeeIndexer(final SolrAdapter solr) {
        this.solr = solr;
    }

    @Incoming("employee-events")
    @Blocking
    public void processEvent(final EmployeeEvent event) {
        if (event instanceof EmployeeCreatedEvent) {
            processEvent((EmployeeCreatedEvent) event);
        } else if (event instanceof EmployeeDeletedEvent) {
            processEvent((EmployeeDeletedEvent) event);
        }
    }

    private void processEvent(final EmployeeCreatedEvent event) {
        LOG.info("Received an EmployeeCreatedEvent: " + event.getEmployeeId());
        SolrInputDocument d = new SolrInputDocument();
        d.setField("id", event.getEmployeeId());
        d.setField("employee_given_name_s", event.getGivenName());
        d.setField("employee_last_name_s", event.getLastName());
        d.setField("employee_department_s", event.getDepartmentName());
        solr.index(d);
    }

    private void processEvent(final EmployeeDeletedEvent event) {
        LOG.info("Received an EmployeeDeletedEvent: " + event.getEmployeeId());
        solr.removeById(event.getEmployeeId());
    }
}
