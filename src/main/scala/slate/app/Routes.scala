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
import akka.http.scaladsl.model.HttpMethods._
import akka.http.scaladsl.model.{Uri, HttpResponse, HttpRequest, HttpEntity}
import akka.http.scaladsl.server
import akka.http.scaladsl.server._
import slate.common.Serializer.{asJson, asHtmlTable}
import slate.http.{HttpUtils, HttpRes}
import spray.json.{DefaultJsonProtocol, JsValue}
import scala.reflect.runtime.{universe => ru}
import slate.common._


object Routes extends Directives
    with RouteConcatenation
    with HttpRes
{

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
      pathSingleSlash {
        complete(HttpEntity("<html><body>Hello world!</body></html>"))
      } ~
        path("ping") {
          complete("PONG !! " + LocalDateTime.now().toString())
        } ~
        path("users" / "register") {
          complete("REGISTER !!")
        } ~
        path("users" / "verify") {
          //entity(as[User])
          complete("VERIFY !!")
        } ~
        path("crash") {
          sys.error("BOOM !!")
        }
    } ~
      post {
        path("users/getAll") {
          complete("post only")
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

    // Example 7: Simple auth via an api key
    paths = paths ~ post {
      path ( model / "auth") { ctx => Auth.ensureApiKey(ctx, (c) => c.complete("auth success!") ) }
    }

    // Example 7: Post with data supplied
    //val route = post {
    //  entity(as[Person]) { person =>
    //    complete(s"Person: ${person.name} - favorite number: ${person.favoriteNumber}")
    //  }
    //}

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
    val paths = route ~ post
    {
      path ( "admin" / "status" / "about"   ) { ctx => Auth.ensureApiKey( ctx, (c) => completeAsHtml(c, asHtmlTable(app.about ) ) ) } ~
      path ( "admin" / "status" / "host"    ) { ctx => Auth.ensureApiKey( ctx, (c) => completeAsHtml(c, asHtmlTable(app.host  ) ) ) } ~
      path ( "admin" / "status" / "lang"    ) { ctx => Auth.ensureApiKey( ctx, (c) => completeAsHtml(c, asHtmlTable(app.lang  ) ) ) } ~
      path ( "admin" / "status" / "info"    ) { ctx => Auth.ensureApiKey( ctx, (c) => completeAsHtml(c, asHtmlTable(app.info()) ) ) } ~
      path ( "admin" / "status" / "infojs"  ) { ctx => Auth.ensureApiKey( ctx, (c) => completeAsJson(c, asJson(app.info()     ) ) ) }
    }

    paths
  }
}
