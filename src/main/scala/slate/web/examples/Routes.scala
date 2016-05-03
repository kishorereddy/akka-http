/**
<slate_header>
  author: Kishore Reddy
  url: https://github.com/kishorereddy/scala-slate
  copyright: 2015 Kishore Reddy
  license: https://github.com/kishorereddy/scala-slate/blob/master/LICENSE.md
  desc: a scala micro-framework
  usage: Please refer to license on github for more info.
</slate_header>
  */

package slate.web.examples

import akka.http.scaladsl.model.HttpMethods._
import akka.http.scaladsl.model.{Uri, HttpResponse, HttpRequest, HttpEntity}
import akka.http.scaladsl.server
import akka.http.scaladsl.server._
import akka.http.scaladsl.server.directives.{MarshallingDirectives, MethodDirectives, PathDirectives, RouteDirectives}
import slate.common.{Host, Lang, About, Reflect}
import slate.core.app.AppSupport
import slate.web.core.{Auth, Utils}

import scala.reflect.runtime.{universe => ru}
import ru._


object Routes extends PathDirectives
    with RouteDirectives
    with RouteConcatenation
    with MethodDirectives
    with MarshallingDirectives
{

  /**
   * example route setup in tree form.
   * @return
   */
  def exampleWithTree() : server.Route = {
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
          //entity(as[User])
          complete("VERIFY !!")
        } ~
        path("crash") {
          sys.error("BOOM !!")
        }
    } ~
      post {
        path("users/delete") {
          complete("post only")
        }
      }
    routes
  }


  /**
   * example route setup with req/res cases
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
   * @param model : "user"
   * @return
   * @note : this builder must implement RouteConcatenation to use the "~"
   */
  def exampleWithModel(model:String):Route = {

    // Build on top of existing sample routes above
    var paths = exampleWithTree()

    // Now add additional routes for the model.
    // NOTE: Ideally post instead of get, but just for examples/demo.
    paths = paths ~  path ( model / "create" )  { complete ( model + " - create")   }
    paths = paths ~  path ( model / "retrieve") { complete ( model + " - retrieve") }
    paths = paths ~  path ( model / "update"  ) { complete ( model + " - update")   }
    paths = paths ~  path ( model / "delete"  ) { complete ( model + " - delete")   }
    paths = paths ~  path ( model / "info"   )  { ctx => ctx.complete(Utils.buildUriParts(ctx.request))}

    // Post
    paths = paths ~ post {
      path ( model / "invite") { ctx => ctx.complete("post") }
    }

    // Auth: api key
    paths = paths ~ post {
      path ( model / "auth") { ctx => Auth.authorize(ctx, (c) => c.complete("auth success!") ) }
    }
    paths
  }


  /**
   * Appends additional routes to the route supplied.
   * @param route
   * @return
   */
  def exampleWithAppendingRoutes(route:Route, app:AppSupport):Route = {

    // lets add some admin specific routes
    val paths = route ~
      path ( "admin" / "status" / "about"   ) { complete ( Reflect.getFields2(app.about )) } ~
      path ( "admin" / "status" / "host"    ) { complete ( Reflect.getFields2(app.host  ) ) } ~
      path ( "admin" / "status" / "lang"    ) { complete ( Reflect.getFields2(app.lang  ) ) }

    paths
  }
}
