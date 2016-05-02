package com.slate.http.examples

import akka.actor.ActorSystem
import akka.event.{Logging, LoggingAdapter}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpMethods._
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import com.slate.http.core.Config

import scala.concurrent.ExecutionContext


/**
 * http://doc.akka.io/docs/akka/2.4.4/scala/http/routing-dsl/overview.html
 * http://doc.akka.io/docs/akka/2.4.4/scala/http/routing-dsl/index.html
 * https://github.com/dnvriend/akka-http-test
 */
object Main extends App with Config {

  private implicit val system = ActorSystem()
  protected implicit val executor: ExecutionContext = system.dispatcher
  protected val log: LoggingAdapter = Logging(system, getClass)
  protected implicit val materializer: ActorMaterializer = ActorMaterializer()


  // start the server
  start2()


  def start1(interface:String = "0.0.0.0", port:Int = 9911): Unit =
  {
    val routes =
      get
      {
          // similar to "/"
          pathSingleSlash {
            complete(HttpEntity("<html><body>Hello world 5/1!</body></html>"))
          } ~
          path("ping") {
            complete("PONG !!")
          } ~
          path("users" / "register") {
            complete("REGISTER !!")
          } ~
          path("users" / "verify") {
            complete("VERIFY !!")
          } ~
          path("crash") {
            sys.error("BOOM !!")
          }
      }
    Http().bindAndHandle(handler = routes, interface = interface, port = port)
  }


  def start2(interface:String = "0.0.0.0", port:Int = 9911): Unit =
  {
    val routes = ModelPathBuilder.buildPathsForModel("users")
    Http().bindAndHandle(handler = routes, interface = interface, port = port)
  }


  def start3(interface:String = "0.0.0.0", port:Int = 9911): Unit =
  {
    val requestHandler: HttpRequest => HttpResponse = {
      case HttpRequest(GET, Uri.Path("/"), _, _, _) =>
        HttpResponse(entity = HttpEntity("<html><body>Hello world!</body></html>"))

      case HttpRequest(GET, Uri.Path("/ping"), _, _, _) =>
        HttpResponse(entity = "PONG!")

      case HttpRequest(GET, Uri.Path("/users/register"), _, _, _) =>
        HttpResponse(entity = "REGISTERED!")

      case HttpRequest(GET, Uri.Path("/crash"), _, _, _) =>
        sys.error("BOOM!")

      case _: HttpRequest =>
        HttpResponse(404, entity = "Unknown resource!")
    }

    Http().bindAndHandleSync(handler = requestHandler, interface = interface, port = port)
  }
}
/*
* How to create a .jar using IntelliJ IDEA 14.1.5:

File > Save All.
Run driver or class with main method.
File > Project Structure.
Select Tab "Artifacts".
Click green plus button near top of window.
Select JAR from Add drop down menu. Select "From modules with dependencies"
Select main class.
The radio button should be selecting "extract to the target JAR." Press OK.
Check the box "Build on make"
Press apply and OK.
From the main menu, select the build dropdown.
Select the option build artifacts.

*/