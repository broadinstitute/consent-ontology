package org.broadinstitute.dsde.consent.ontology.service;

import org.broadinstitute.dsde.consent.ontology.model.TermResource;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

public interface AutocompleteService {
    List<TermResource> lookup(String query, int limit);
    List<TermResource> lookup(Collection<String> tags, String query, int limit);
    List<TermResource> lookupById(String query) throws IOException;
}
