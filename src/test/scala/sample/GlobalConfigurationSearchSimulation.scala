package sample

import io.gatling.core.Predef._
import io.gatling.http.Predef._


class GlobalConfigurationSearchSimulation extends Simulation {

  val httpConf = http
    .baseURL("http://192.168.99.100/products")

  val scn = scenario("BasicSimulation")
    .exec(http("SearchRequest")
      .get("/washing+machine"))
    .pause(5)

  setUp(
    scn.inject(atOnceUsers(10))
  ).protocols(httpConf)
    .assertions(
      global.responseTime.max.lessThan(50),
      global.successfulRequests.percent.greaterThan(99),
      global.failedRequests.percent.is(0))
}





