package org.broadinstitute.dsde.consent.ontology.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.broadinstitute.dsde.consent.ontology.configurations.ElasticSearchConfiguration;
import org.elasticsearch.client.RestClient;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ElasticSearchSupportTest {

    private ElasticSearchConfiguration configuration;
    private ElasticSearchSupport elasticSearchSupport;

    @Before
    public void setUp() {
        configuration = new ElasticSearchConfiguration();
        configuration.setIndex("local-ontology");
        configuration.setServers(Collections.singletonList("localhost"));
        this.elasticSearchSupport = new ElasticSearchSupport();
    }

    @Test
    public void testGetRestClient() {
        RestClient client = elasticSearchSupport.createRestClient(configuration);
        Assert.assertNotNull(client);
    }

    @Test
    public void testGetIndexPath() {
        String path = elasticSearchSupport.getIndexPath(configuration.getIndex());
        Assert.assertNotNull(path);
        Assert.assertTrue(path.contains(configuration.getIndex()));
    }

    @Test
    public void testGetClusterHealthPath() {
        String path = elasticSearchSupport.getClusterHealthPath();
        Assert.assertNotNull(path);
        Assert.assertTrue(path.contains("health"));
    }

    @Test
    public void testBuildFilterQuery() {
        String termId = "term_id";
        List<String> filters = Arrays.asList("Disease", "Organization");
        String query = elasticSearchSupport.buildFilterQuery(termId, filters);

        JsonObject jsonQuery = JsonParser.parseString(query).getAsJsonObject();
        Assert.assertTrue(jsonQuery.has("query"));

        JsonObject joQuery = jsonQuery.getAsJsonObject("query");
        Assert.assertTrue(joQuery.has("bool"));

        JsonObject joBool = joQuery.getAsJsonObject("bool");
        Assert.assertTrue(joBool.has("must"));
        Assert.assertTrue(joBool.has("filter"));


        JsonObject joMust = joBool.getAsJsonObject("must");
        Assert.assertTrue(joMust.has("multi_match"));

        JsonObject joMultiMatch = joMust.getAsJsonObject("multi_match");
        Assert.assertTrue(joMultiMatch.has("query"));
        Assert.assertEquals(joMultiMatch.get("query").getAsString(), termId);
        Assert.assertTrue(joMultiMatch.has("type"));
        Assert.assertTrue(joMultiMatch.has("fields"));
        Assert.assertEquals(joMultiMatch.getAsJsonArray("fields").size(), elasticSearchSupport.searchFields.length);

        JsonArray joFilter = joBool.getAsJsonArray("filter");
        Assert.assertEquals(2, joFilter.size());

    }

}
