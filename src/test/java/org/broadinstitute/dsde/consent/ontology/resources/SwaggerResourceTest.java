package org.broadinstitute.dsde.consent.ontology.resources;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.broadinstitute.dsde.consent.ontology.resources.SwaggerResource.MEDIA_TYPE_CSS;
import static org.broadinstitute.dsde.consent.ontology.resources.SwaggerResource.MEDIA_TYPE_JS;
import static org.broadinstitute.dsde.consent.ontology.resources.SwaggerResource.MEDIA_TYPE_PNG;

public class SwaggerResourceTest {

    private SwaggerResource swaggerResource;

    @Before
    public void setUp() {
        swaggerResource = new SwaggerResource();
    }

    @Test
    public void testIndex() {
        Response response = swaggerResource.content("index.html");
        checkStatusAndHeader(response, MediaType.TEXT_HTML);
        String content = response.getEntity().toString().trim();
        Assert.assertTrue(content.contains("<!DOCTYPE html>"));
        Assert.assertTrue(content.contains("</html>"));
    }

    @Test
    public void testNotFound() {
        Response response = swaggerResource.content("foo/bar.txt");
        Assert.assertEquals(response.getStatus(), Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    public void testImageNotFound() {
        Response response = swaggerResource.content("foo/bar.png");
        Assert.assertEquals(response.getStatus(), Response.Status.NOT_FOUND.getStatusCode());
    }

    private void checkStatusAndHeader(Response response, String header) {
        Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Object headerObject = response.getHeaders().get("Content-type");
        Assert.assertTrue(headerObject.toString().contains(header));
    }
}
