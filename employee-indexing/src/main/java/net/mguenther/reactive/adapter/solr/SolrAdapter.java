package net.mguenther.reactive.adapter.solr;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class SolrAdapter {

    private static final Logger LOG = Logger.getLogger(SolrAdapter.class);

    private static final int DEFAULT_COMMIT_WITHIN_MS = 30_000;

    private final SolrClient solr;

    @Inject
    public SolrAdapter(final SolrClient solr) {
        this.solr = solr;
    }

    public boolean index(SolrInputDocument doc) {
        return index(List.of(doc));
    }

    public boolean index(List<SolrInputDocument> docs) {
        var successful = true;
        if (docs == null || docs.size() == 0) return successful;
        try {
            solr.add(docs, DEFAULT_COMMIT_WITHIN_MS);
            solr.commit();
        } catch (Exception e) {
            LOG.warn("Unable to feed collection of {} documents to Solr.", docs.size(), e);
            successful = false;
        }
        return successful;
    }

    public boolean removeById(String id) {
        var successful = true;
        try {
            solr.deleteByQuery("id:" + id);
            solr.commit();
        } catch (Exception e) {
            LOG.warn("Unable to remove the requested document with ID {} from Solr.", id, e);
            successful = false;
        }
        return successful;
    }
}
