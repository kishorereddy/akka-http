/**
  * <slate_header>
  * author: Kishore Reddy
  * url: https://github.com/kishorereddy/scala-slate
  * copyright: 2015 Kishore Reddy
  * license: https://github.com/kishorereddy/scala-slate/blob/master/LICENSE.md
  * desc: a scala micro-framework
  * usage: Please refer to license on github for more info.
  * </slate_header>
  */

package slate.app


import java.time.LocalDateTime
import akka.actor.ActorSystem
import akka.http.javadsl.model.headers.ContentType
import akka.http.scaladsl.model.HttpMethods._
import akka.http.scaladsl.model._
import akka.http.scaladsl.model.headers.RawHeader
import akka.http.scaladsl.server
import akka.http.scaladsl.server._
import akka.stream.ActorMaterializer
import slate.common.Serializer.{asJson, asHtmlTable}
import slate.http.{HttpJson, HttpUtils, HttpRes}
import spray.json.JsValue
import scala.concurrent.ExecutionContext
import scala.reflect.runtime.{universe => ru}
import slate.common._


object Routes extends Directives
    with RouteConcatenation
    with HttpRes
{
  implicit var system:ActorSystem = ActorSystem()
  implicit var executor:ExecutionContext = system.dispatcher
  implicit var materializer:ActorMaterializer = ActorMaterializer()


  def init(actorSys:ActorSystem, exec:ExecutionContext, mat:ActorMaterializer):Unit = {
    system = actorSys
    executor = system.dispatcher
    materializer = mat
  }

  /**
   * example route setup in tree form.
    *
    * @return
   */
  def exampleWithTree() : server.Route = {
  val routes =
    get
    {
      // similar to "/"
      // MediaTypes.`text/plain`.withCharset(HttpCharsets.`UTF-8`)
      pathSingleSlash {
        complete("<html><body>Hello world 2!</body></html>")
      } ~
        path("ping") {
          complete("DEPLOYED PONG 2 !! " + LocalDateTime.now().toString())
        } ~
        path("users" / "register") {
          complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`, "REGISTER !!"))
        } ~
        path("users" / "verify") {
          //entity(as[User])
          complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`, "VERIFY !!"))
        } ~
        path("crash") {
          sys.error("BOOM !!")
        }
    } ~
      post {
        path("users/getAll") {
          complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`,"post only"))
        }
      }
    routes
  }


  /**
   * example route setup with req/res cases
    *
    * @return
   */
  def exampleWithCases: (HttpRequest) => HttpResponse = {

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
    requestHandler
  }


  /**
   * example route setup for a model.
   * builds up a crud path ( create, retrieve, update, delete ) for the model name specified.
    *
    * @param model : "user"
   * @return
   * @note : this builder must implement RouteConcatenation to use the "~"
   */
  def exampleWithModel(model:String):Route = {

    import HttpJson._

    // Build on top of existing sample routes above
    var paths = exampleWithTree()

    // Now add additional routes for the model.
    // NOTE: Ideally post instead of get, but just for examples/demo.

    // Example 1: basic - /users/create | edit via post
    paths = paths ~ post {
      path ( model / "create" ) { ctx => ctx.complete ( model + " - create"   ) } ~
      path ( model / "update" ) { ctx => ctx.complete ( model + " - update"   ) }
    }

    // Example 2: Id - /users/get/2 via get
    paths = paths ~  path ( model / "get"    / IntNumber ) { id  => complete ( model + " - get " + id) }

    // Example 3: Show uri - /users/info?id=abc
    paths = paths ~  path ( model / "about"   ) { ctx => ctx.complete(HttpUtils.buildUriParts(ctx.request))}

    // Example 4: Post with id - /users/delete/4
    paths = paths ~  post {
      path(model / "delete" / IntNumber) { id => complete(model + " - delete") }
    }

    // Example 5: Regex action name /users/action/anything
    paths = paths ~ path ( model / "action" / """(\w+)""".r ) { name => complete("status : " + name ) }

    // Example 6: "api/{area}/{service}/{action}
    paths = paths ~ path ( "api" / Segment.repeat(3, separator = Slash) ) { parts => complete("parts:" + parts.toString()) }

    // Example 7: Simple auth via an api key
    paths = paths ~ post {
      path ( model / "auth") { ctx => Auth.ensureApiKey(ctx, (c) => c.complete("auth success!") ) }
    }

    // Example 8: Post with json data supplied
    paths = paths ~ path("invites" / "create") {
      post {
        entity(as[JsValue]) { jsData =>
          complete("json data from routes: " + jsData.toString())
        }
      }
    }

    paths
  }


  /**
   * Appends additional routes to the route supplied.
   *
   * @param route
   * @return
   */
  def exampleWithAppendingRoutes(route:Route, app:AppSupport):Route = {

    // lets add some admin specific routes
    // HttpEntity(`application/json`, error._2)
    val paths = route ~ get
    {
      path ( "admin" / "status" / "about"   ) { ctx => completeAsHtml(ctx, asHtmlTable(app.about ) ) } ~
      path ( "admin" / "status" / "host"    ) { ctx => completeAsHtml(ctx, asHtmlTable(app.host  ) ) } ~
      path ( "admin" / "status" / "lang"    ) { ctx => completeAsHtml(ctx, asHtmlTable(app.lang  ) ) } ~
      path ( "admin" / "status" / "info"    ) { ctx => completeAsHtml(ctx, asHtmlTable(app.info()) ) } ~
      path ( "admin" / "status" / "infojs"  ) { ctx => completeAsJson(ctx, asJson(app.info()     ) ) }
    }

    paths
  }
}
