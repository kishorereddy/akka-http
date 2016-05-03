package slate.web.examples

import akka.actor.ActorSystem
import akka.event.{Logging, LoggingAdapter}
import akka.http.scaladsl.{server, Http}
import akka.http.scaladsl.model.HttpMethods._
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import slate.common.{About, Config}
import slate.core.app.AppSupport

import scala.concurrent.ExecutionContext


/**
 * http://doc.akka.io/docs/akka/2.4.4/scala/http/routing-dsl/overview.html
 * http://doc.akka.io/docs/akka/2.4.4/scala/http/routing-dsl/index.html
 * https://github.com/dnvriend/akka-http-test
 * http://chariotsolutions.com/blog/post/akka-http-getting-started/
 */
object WebApp extends App with Config with AppSupport {

  private implicit val system = ActorSystem()
  protected implicit val executor: ExecutionContext = system.dispatcher
  protected val log: LoggingAdapter = Logging(system, getClass)
  protected implicit val materializer: ActorMaterializer = ActorMaterializer()
  protected val _interface = "0.0.0.0"
  protected val _port = 9911


  // start the server
  startup( () => setupViaChaining() )


  override def init() : Unit =
  {
    super.init()

    // You can pull this from .conf files, just setting up here for examples purposes.
    _about = new About(
      name = "Sample Akka Http Web Api",
      desc = "Show various ways to setup routes, web apis, and some general patterns and practices",
      group = "your group",
      region = "n/a",
      url = "http://www.mycompany.com",
      contact = "kishore@abc.com",
      version = "1.0.1.7",
      tags = "scala,akka,akka-http,web,apis,routes"
    )
  }


  def startup(callback:() => Unit) : Unit =
  {
    init()
    callback()
  }


  /**
   * Route setup using simple cases
   */
  def setupViaCases(): Unit =
  {
    val requestHandler: (HttpRequest) => HttpResponse = Routes.exampleWithCases
    Http().bindAndHandleSync(handler = requestHandler, interface = _interface, port = _port)
  }


  /**
   * Route setup using explicit route tree
   */
  def setupViaFlow(): Unit =
  {
    val routes: Route = Routes.exampleWithTree()
    Http().bindAndHandle(handler = routes, interface = _interface, port = _port)
  }


  /**
   * Route setup dynamically using crud operations on a model
   */
  def setupViaDynamic(): Unit =
  {
    val routes = Routes.exampleWithModel("users")
    Http().bindAndHandle(handler = routes, interface = _interface, port = _port)
  }


  /**
   * Route setup dynamically using crud operations on a model
   */
  def setupViaChaining(): Unit =
  {
    val routes = Routes.exampleWithModel("users")
    val finalRoutes = Routes.exampleWithAppendingRoutes(routes, this)
    Http().bindAndHandle(handler = finalRoutes, interface = _interface, port = _port)
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