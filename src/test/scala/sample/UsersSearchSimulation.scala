package sample                                            // 1 The optional package.

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._

class UsersSearchSimulation extends Simulation {               // 3 Note that it extends Simulation

  val stdUser1 = scenario("Standard User1").exec(http("searchRequest").get("/ring"))
  val stdUser2 = scenario("Standard User2").exec(http("searchRequest").get("/washing+machine"))

  val httpConf = http                                     // 4 The common configuration to all HTTP requests.
    .baseURL("http://192.168.99.100/products")            // 5 The baseURL that will be prepended to all relative urls

  setUp(
    stdUser1.inject(atOnceUsers(200)),
    stdUser2.inject(nothingFor(6 seconds), rampUsers(500) over (2 seconds)))
    .protocols(httpConf)
}