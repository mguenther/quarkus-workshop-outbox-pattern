package net.mguenther.reactive;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Produces;
import org.apache.solr.client.solrj.impl.HttpSolrClient;

@ApplicationScoped
public class BeanConfig {

    @Produces
    @ApplicationScoped
    public HttpSolrClient solrClient() {
        var builder = new HttpSolrClient.Builder();
        builder.withBaseSolrUrl("http://localhost:8983/solr/employees");
        return builder.build();
    }
}
