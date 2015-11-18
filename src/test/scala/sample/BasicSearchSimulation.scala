package sample

// 2 The required package
import io.gatling.core.Predef._
import io.gatling.http.Predef._

// 3 Note that it extends Simulation
class BasicSearchSimulation extends Simulation {

   val httpConf = http                                     // 4 The common configuration to all HTTP requests.
     .baseURL("http://192.168.99.100/products")            // 5 The baseURL that will be prepended to all relative urls

   val scn = scenario("BasicSimulation")                   // 7 The scenario definition
     .exec(http("SearchRequest")                           // 8 A HTTP request, named searchRequest. This name will be displayed in the final reports.
     .get("/washing+machine"))                               // 9 The url this request targets with the GET method
     .pause(5)                                             // 10 Some pause/think time.

   setUp(                                                  // 11 Where one sets up the scenarios that will be launched in this Simulation.
     scn.inject(atOnceUsers(10))                            // 12 Declaring to inject into scenario named scn one single user
   ).protocols(httpConf)                                   // 13 Attaching the HTTP configuration declared above
}





