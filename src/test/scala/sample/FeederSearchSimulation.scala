package sample                                            // 1 The optional package.

import io.gatling.core.Predef._
import io.gatling.http.Predef._

// Feeder example
// Global configuration and
// Http Check
class FeederSearchSimulation extends Simulation {               // 3 Note that it extends Simulation

  // As the default feeder strategy is queue,
  // we will use the random strategy for this test to avoid feeder starvation.
  val feeder = csv("search.csv").random

  val httpConf = http                                     // 4 The common configuration to all HTTP requests.
    .baseURL("http://192.168.99.100/products")            // 5 The baseURL that will be prepended to all relative urls

  val scn = scenario("FeederSearchSimulation")                   // 7 The scenario definition
    .feed(feeder)
     exec(http("SearchRequest")                        // let's give proper names, as they are displayed in the reports
    .get("/${searchTerm}"))                                // 9 The url this request targets with the GET method
//    .check(status.is(200), responseTimeInMillis.lessThan(50L), regex("${searchTerm}").find(1).exists))
    .pause(3)

  setUp(                                                  // 11 Where one sets up the scenarios that will be launched in this Simulation.
    scn.inject(atOnceUsers(10))                           // 12 Declaring to inject into scenario named scn one single user
  ).protocols(httpConf)                                   // 13 Attaching the HTTP configuration declared above
  .assertions(
  global.responseTime.max.lessThan(50),
  global.successfulRequests.percent.greaterThan(99))
  global.failedRequests.percent.is(0)
}

object Search {

  // As the default feeder strategy is queue,
  // we will use the random strategy for this test to avoid feeder starvation.
  val feeder = csv("search.csv").random

  val search = exec(http("Search")                        // let's give proper names, as they are displayed in the reports
    .get("/${searchTerm}")                                // 9 The url this request targets with the GET method
    .check(status.is(200), responseTimeInMillis.lessThan(50L), regex("${searchTerm}").find(1).exists))
    .pause(5)
    .feed(feeder)
}