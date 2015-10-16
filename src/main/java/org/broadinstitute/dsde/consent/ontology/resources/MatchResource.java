package org.broadinstitute.dsde.consent.ontology.resources;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.dispatch.ExecutionContexts;
import akka.dispatch.OnComplete;
import akka.pattern.Patterns;
import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.concurrent.ExecutionContextExecutorService;
import scala.concurrent.Future;

import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import org.broadinstitute.dsde.consent.ontology.actor.OntologyMatchingActor;
import org.broadinstitute.dsde.consent.ontology.datause.ontologies.OntologyModel;

@Path("/match")
@Consumes("application/json")
@Produces("application/json")
public class MatchResource {

    private final Logger log = LoggerFactory.getLogger(MatchResource.class);
    private static final ActorSystem actorSystem = ActorSystem.create("actorSystem");
    private static final ActorRef ontologyMatchingActor = actorSystem.actorOf(Props.create(OntologyMatchingActor.class), "OntologyMatchingActor");
//    private ActorRef ontologyMatchingActor;
    private final ExecutionContextExecutorService execCtx = ExecutionContexts.fromExecutorService(Executors.newFixedThreadPool(2));;
    private OntologyModel ontologyList;
        
    public MatchResource() {
    }

    @POST
    public void match(@Suspended final AsyncResponse response, final MatchPair matchPair) throws Exception {
        // TODO: Timeout should likely be an application-wide property
        Long timeout = 10000L;
        log.debug("Received the following: " + matchPair.toString());
        final MatchDTO matchDTO = new MatchDTO(matchPair, ontologyList);
        final Future<Object> matchFuture = Patterns.ask(ontologyMatchingActor, matchDTO, timeout);
        matchFuture.onComplete(
            new OnComplete<Object>() {
                @Override
                public void onComplete(Throwable failure, Object result) {
                    if (failure != null) {
                        System.out.println("Failure: " + failure.getMessage());
                        response.resume(new WebApplicationException(failure));
                    } else {
                        response.resume(ImmutableMap.of("result", result, "matchPair", matchPair).toString());
                    }
                }
            },
            execCtx
        );
    }

    @Inject
    public void setOntologyList(OntologyModel ontologyList) {
        this.ontologyList = ontologyList;
    }
}
