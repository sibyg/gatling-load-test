package sample


import io.gatling.core.Predef._
import io.gatling.http.Predef._

class HttpCheckSearchSimulation extends Simulation {

  // this file needs to be in user-files/data dir
  val feeder = csv("argosSearch.csv").random

  val httpConf = http
    .baseURL("http://192.168.99.100/products")

  val scn = scenario("BasicSimulation")
    .feed(feeder)
    .exec(http("SearchRequest")
      .get("/${searchTerm}")
      .check(status.is(200), responseTimeInMillis.lessThan(50L), regex("${searchTerm}").find(1).exists))
    .pause(5)

  setUp(scn.inject(atOnceUsers(10)).protocols(httpConf))
}
