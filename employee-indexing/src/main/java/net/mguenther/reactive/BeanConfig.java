package net.mguenther.reactive;

import org.apache.solr.client.solrj.impl.HttpSolrClient;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

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
