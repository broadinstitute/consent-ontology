package org.broadinstitute.dsp.ontology

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef.http
import io.gatling.http.protocol.HttpProtocolBuilder
import org.broadinstitute.dsp.ontology.requests.Requests

class DefaultScenarios extends Simulation {

  // TODO: Some requests accept plain text. Can we abstract that out per scenario?
  val httpProtocol: HttpProtocolBuilder = http
    .baseUrl("https://consent-ontology.dsde-dev.broadinstitute.org")
    .acceptHeader(Config.applicationJson)
    .userAgentHeader(Config.defaultUserAgent)

  val defaultScenarios: List[ScenarioBuilder] = List(
    scenario("Status Scenario").exec(Requests.statusRequest)
  )

  setUp(
    defaultScenarios.map(scn =>
      scn
        .pause(Config.defaultPause)
        .inject(atOnceUsers(Config.defaultUsers))
    )
  ).protocols(httpProtocol)

}
