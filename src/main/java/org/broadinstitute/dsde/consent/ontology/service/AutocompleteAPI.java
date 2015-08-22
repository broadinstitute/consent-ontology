package org.broadinstitute.dsde.consent.ontology.service;

import org.broadinstitute.dsde.consent.ontology.resources.TermResource;

import java.util.List;

public interface AutocompleteAPI {
    List<TermResource> lookup(String query, int limit);
    List<TermResource> lookup(String[] tags, String query, int limit);
}