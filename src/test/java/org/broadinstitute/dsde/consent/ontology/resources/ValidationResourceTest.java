package org.broadinstitute.dsde.consent.ontology.resources;

import com.google.api.client.http.HttpStatusCodes;
import org.broadinstitute.dsde.consent.ontology.service.UseRestrictionValidationService;
import org.broadinstitute.dsde.consent.ontology.model.ValidationResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Response;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ValidationResourceTest {

    private ValidationResource validationResource;

    @Mock
    private UseRestrictionValidationService validationService;

    private static final String useRestriction = "{ + \"type\": \"and\","
                                   + "\"operands\": [{ \"type\": \"named\","
                                   + "\"name\": \"http://purl.obolibrary.org/obo/DOID_162\""
                                   + "}]}";
    private static final String invalidUseRestriction = "{\"test\": \"and\","
                                    + "\"operands\": [{ \"type\": \"named\","
                                    + "\"name\": \"http://purl.obolibrary.org/obo/DOID_162\""
                                    + "}]}";

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        validationResource = new ValidationResource(validationService);
    }

    @Test
    public void testValidateUseRestriction() throws Exception {
        ValidationResponse validationResponse = new ValidationResponse(true, useRestriction);
        when(validationService.validateUseRestriction(useRestriction)).thenReturn(
            validationResponse);
        Response response = validationResource.validateUseRestriction(useRestriction);
        ValidationResponse validationResponseResult = (ValidationResponse) response.getEntity();
        assertTrue(validationResponseResult.getErrors().isEmpty());
        assertTrue(validationResponseResult.isValid());
        assertEquals(response.getStatus(), HttpStatusCodes.STATUS_CODE_OK);
        assertTrue(validationResponseResult.getUseRestriction().endsWith(useRestriction));
    }

    @Test
    public void testValidateUseRestrictionInvalid() throws Exception {
        String error = "Term not found: test";
        ValidationResponse validationResponse = new ValidationResponse(false, invalidUseRestriction);
        validationResponse.addError(error);
        when(validationService.validateUseRestriction(useRestriction)).thenReturn(
            validationResponse);
        Response response = validationResource.validateUseRestriction(useRestriction);
        ValidationResponse validationResponseResult = (ValidationResponse) response.getEntity();
        assertFalse(validationResponseResult.isValid());
        assertEquals(response.getStatus(), Response.Status.OK.getStatusCode());
        assertTrue(((ArrayList<String>) validationResponseResult.getErrors()).get(0).contains(error));
    }

    @Test
    public void testValidateUseRestrictionAfterException() throws Exception {
        doThrow(new Exception()).when(validationService).validateUseRestriction(useRestriction);
        Response response = validationResource.validateUseRestriction(useRestriction);
        assertEquals(response.getStatus(), Response.Status.BAD_REQUEST.getStatusCode());
    }

}
