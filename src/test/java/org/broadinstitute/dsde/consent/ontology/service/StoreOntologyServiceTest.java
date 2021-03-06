package org.broadinstitute.dsde.consent.ontology.service;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpStatusCodes;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.LowLevelHttpRequest;
import com.google.api.client.http.LowLevelHttpResponse;
import com.google.api.client.json.Json;
import com.google.api.client.testing.http.HttpTesting;
import com.google.api.client.testing.http.MockHttpTransport;
import com.google.api.client.testing.http.MockLowLevelHttpRequest;
import com.google.api.client.testing.http.MockLowLevelHttpResponse;
import org.broadinstitute.dsde.consent.ontology.cloudstore.CloudStore;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.broadinstitute.dsde.consent.ontology.datause.builder.UseRestrictionBuilderSupport.METHODS_RESEARCH;
import static org.broadinstitute.dsde.consent.ontology.datause.builder.UseRestrictionBuilderSupport.RESEARCH_TYPE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StoreOntologyServiceTest {

    private static final String content = "{\"name\":\"" + METHODS_RESEARCH + "\"}";
    private static final String urls = "{\"" + RESEARCH_TYPE + "\":\"" + METHODS_RESEARCH + "\"}";
    private static final String error = "{\"error:\":\"Not Found\"}";

    @Mock
    CloudStore store;
    private StoreOntologyService storeOntologyService;

    @Before
    public void setUpClass() {
        MockitoAnnotations.initMocks(this);
        storeOntologyService = new StoreOntologyService(store, "ontology", "ontology");
    }

    @Test
    public void testRetrieveConfigurationFile() throws Exception {
        HttpResponse response = getHttpResponse(content);
        when(store.getStorageDocument(Mockito.anyString())).thenReturn(response);
        String result = storeOntologyService.retrieveConfigurationFile();
        assertTrue(result.contains(content));
    }

    @Test
    public void testRetrieveConfigurationFileWithNullResponse() throws Exception {
        when(store.getStorageDocument(Mockito.anyString())).thenReturn(null);
        String result = storeOntologyService.retrieveConfigurationFile();
        assertTrue(result.isEmpty());
    }

    @Test
    public void testRetrieveOntologyURLs() throws Exception {
        HttpResponse httpResponse = getHttpResponse(urls);
        when(store.getStorageDocument(Mockito.anyString())).thenReturn(httpResponse);
        List<URL> urls = new ArrayList<>(storeOntologyService.retrieveOntologyURLs());
        assertFalse(urls.isEmpty());
        assertEquals(urls.get(0), new URL(RESEARCH_TYPE));
    }

    @Test
    public void testRetrieveOntologyURLWithInvalidFormat() throws Exception {
        HttpResponse httpResponse = getHttpResponse(error);
        when(store.getStorageDocument(Mockito.anyString())).thenReturn(httpResponse);
        assertTrue(storeOntologyService.retrieveOntologyURLs().isEmpty());
    }

    private HttpResponse getHttpResponse(String content) throws IOException {
        HttpTransport transport = new MockHttpTransport() {
            @Override
            public LowLevelHttpRequest buildRequest(String method, String url) {
                return new MockLowLevelHttpRequest() {
                    @Override
                    public LowLevelHttpResponse execute() {
                        MockLowLevelHttpResponse response = new MockLowLevelHttpResponse();
                        response.setStatusCode(HttpStatusCodes.STATUS_CODE_OK);
                        response.setContentType(Json.MEDIA_TYPE);
                        response.setContent(content);
                        return response;
                    }
                };
            }
        };
        HttpRequest request = transport.createRequestFactory().buildGetRequest(HttpTesting.SIMPLE_GENERIC_URL);
        return request.execute();
    }

}
