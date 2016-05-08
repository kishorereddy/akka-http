package slate.app

import akka.actor.ActorSystem
import akka.event.{Logging, LoggingAdapter}
import akka.http.scaladsl.{Http}
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.{Directives, Route}
import akka.stream.ActorMaterializer
import slate.common.{AppSupport, About, Config}

import scala.concurrent.ExecutionContext



/**
 * http://doc.akka.io/docs/akka/2.4.4/scala/http/routing-dsl/overview.html
 * http://doc.akka.io/docs/akka/2.4.4/scala/http/routing-dsl/index.html
 * https://github.com/dnvriend/akka-http-test
 * http://chariotsolutions.com/blog/post/akka-http-getting-started/
 * http://boldradius.com/blog-post/VNofuCYAACUAiO7w/how-to-configure-an-akka-cluster-on-amazon-ec2-instances-aws
 * http://chrisloy.net/2014/05/11/akka-cluster-ec2-autoscaling.html
 */
object WebApp extends App with Config with AppSupport with  Directives {

  private   implicit val system = ActorSystem()
  protected implicit val executor: ExecutionContext = system.dispatcher
  protected implicit val materializer: ActorMaterializer = ActorMaterializer()
  protected val log: LoggingAdapter = Logging(system, getClass)
  protected val _interface = "::0"
  protected val _port = 5000


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

    // Initialize the routes with the implicits so
    // that it can handle the deserialization of json data in HttpEntity.
    Routes.init(system, executor, materializer)
  }


  /**
   * startup with life-cycle hooks.
    *
    * @param startCallback
   */
  def startup(startCallback:() => Unit) : Unit =
  {
    init()
    onBeforeStartup()
    startCallback()
    onAfterStartup()
  }


  /**
   * Route setup using simple cases
   */
  def setupViaCases(): Unit =
  {
    val requestHandler: (HttpRequest) => HttpResponse = Routes.exampleWithCases
    Http().bindAndHandleSync(handler = requestHandler, interface = _interface, port = getPort)
  }


  /**
   * Route setup using explicit route tree
   */
  def setupViaFlow(): Unit =
  {
    val routes: Route = Routes.exampleWithTree()
    Http().bindAndHandle(handler = routes, interface = _interface, port = getPort)
  }


  /**
   * Route setup dynamically using crud operations on a model
   */
  def setupViaDynamic(): Unit =
  {
    val routes = Routes.exampleWithModel("users")
    Http().bindAndHandle(handler = routes, interface = _interface, port = getPort)
  }


  /**
   * Route setup dynamically using crud operations on a model
   */
  def setupViaChaining(): Unit =
  {
    var routes = Routes.exampleWithModel("users")
    routes = Routes.exampleWithAppendingRoutes(routes, this)
    Http().bindAndHandle(handler = routes, interface = _interface, port = getPort)
  }


  /**
   * called on before the routes are setup and server is listening for requests
   */
  def onBeforeStartup():Unit =
  {
    println("before startup")
  }


  /**
   * called on after the routes are setup and server is listening for requests
    */
  def onAfterStartup():Unit =
  {
    println("after startup")
  }

  def getPort():Int = {
    val txt = System.getenv().get("PORT")
    Integer.parseInt(txt)
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


# Configure logging in SBT:
sbt
> set logLevel in run := Level.Debug


# Classpath setup by SBT:
[debug]   Classpath:
C:\Dev\Tests\akka-http\target\scala-2.11\classes
C:\Dev\Tests\akka-http\lib\akka-actor_2.11-2.3.11.jar
C:\Dev\Tests\akka-http\lib\akka-http-core-experimental_2.11-1.0-RC4.jar
C:\Dev\Tests\akka-http\lib\akka-http-experimental_2.11-1.0-RC4.jar
C:\Dev\Tests\akka-http\lib\akka-http-spray-json-experimental_2.11-1.0-RC4.jar
C:\Dev\Tests\akka-http\lib\akka-http-testkit-experimental_2.11-1.0-RC4.jar
C:\Dev\Tests\akka-http\lib\akka-stream-experimental_2.11-1.0-RC4.jar
C:\Dev\Tests\akka-http\lib\akka-testkit_2.11-2.3.11.jar
C:\Dev\Tests\akka-http\lib\scala-reflect.jar
C:\Dev\Tests\akka-http\lib\scala-xml_2.11-1.0.4.jar
C:\Dev\Tests\akka-http\lib\slate-http-restapi_2.11-0.0.1.jar
C:\Users\kreddy\.ivy2\cache\org.scala-lang\scala-library\jars\scala-library-2.11.6.jar
C:\Users\kreddy\.ivy2\cache\com.typesafe.akka\akka-actor_2.11\jars\akka-actor_2.11-2.3.11.jar
C:\Users\kreddy\.ivy2\cache\com.typesafe\config\bundles\config-1.2.1.jar
C:\Users\kreddy\.ivy2\cache\com.typesafe.akka\akka-stream-experimental_2.11\jars\akka-stream-experimental_2.11-1.0-RC4.jar
C:\Users\kreddy\.ivy2\cache\org.reactivestreams\reactive-streams\jars\reactive-streams-1.0.0.jar
C:\Users\kreddy\.ivy2\cache\com.typesafe.akka\akka-http-experimental_2.11\jars\akka-http-experimental_2.11-1.0-RC4.jar
C:\Users\kreddy\.ivy2\cache\com.typesafe.akka\akka-http-core-experimental_2.11\jars\akka-http-core-experimental_2.11-1.0-RC4.jar
C:\Users\kreddy\.ivy2\cache\com.typesafe.akka\akka-parsing-experimental_2.11\jars\akka-parsing-experimental_2.11-1.0-RC4.jar
C:\Users\kreddy\.ivy2\cache\com.typesafe.akka\akka-http-spray-json-experimental_2.11\jars\akka-http-spray-json-experimental_2.11-1.0-RC4.jar
C:\Users\kreddy\.ivy2\cache\io.spray\spray-json_2.11\bundles\spray-json_2.11-1.3.1.jar
C:\Users\kreddy\.ivy2\cache\com.typesafe.akka\akka-http-testkit-experimental_2.11\jars\akka-http-testkit-experimental_2.11-1.0-RC4.jar
C:\Users\kreddy\.ivy2\cache\com.typesafe.akka\akka-testkit_2.11\jars\akka-testkit_2.11-2.3.11.jar

*/