package org.broadinstitute.dsde.consent.ontology.model;

import java.net.URL;
import java.util.Collection;
import org.broadinstitute.dsde.consent.ontology.resources.MatchPair;

@SuppressWarnings("WeakerAccess")
public class MatchWorkerMessage {

    Collection<URL> urlCollection;

    MatchPair matchPair;

    public MatchWorkerMessage(Collection<URL> urlCollection, MatchPair matchPair) {
        setUrlCollection(urlCollection);
        setMatchPair(matchPair);
    }

    public Collection<URL> getUrlCollection() {
        return urlCollection;
    }

    public void setUrlCollection(Collection<URL> urlCollection) {
        this.urlCollection = urlCollection;
    }

    public MatchPair getMatchPair() {
        return matchPair;
    }

    public void setMatchPair(MatchPair matchPair) {
        this.matchPair = matchPair;
    }

}
