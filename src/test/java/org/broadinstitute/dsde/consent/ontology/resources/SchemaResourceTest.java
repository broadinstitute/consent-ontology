package org.broadinstitute.dsde.consent.ontology.resources;

import org.broadinstitute.dsde.consent.ontology.datause.models.Everything;
import org.broadinstitute.dsde.consent.ontology.datause.models.UseRestriction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class SchemaResourceTest {

    SchemaResource schemaResource;
    String generalUse = "{ \"generalUse\": true }";

    @Before
    public void setUp() throws Exception {
        schemaResource = new SchemaResource();
    }

    @Test
    public void testIndex() {
        Response response = schemaResource.getSchema();
        assertStatusAndHeader(response, Response.Status.OK, MediaType.APPLICATION_JSON);
        String content = response.getEntity().toString().trim();
        Assert.assertTrue(content.contains("Data Use Schema"));
    }

    @Test
    public void testTranslateGeneralUse() {
        Response response = schemaResource.translate(generalUse);
        UseRestriction restriction = (UseRestriction) response.getEntity();
        assertNotNull(restriction);
        assertTrue(restriction.equals(new Everything()));
        assertStatusAndHeader(response, Response.Status.OK, MediaType.APPLICATION_JSON);
    }

    private void assertStatusAndHeader(Response response, Response.Status status, String contentType) {
        Assert.assertTrue(response.getStatus() == status.getStatusCode());
        Object header = response.getHeaders().get("Content-type");
        Assert.assertTrue(header.toString().contains(contentType));
    }

}
