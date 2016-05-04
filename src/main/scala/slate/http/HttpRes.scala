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

package slate.http

import akka.http.scaladsl.model.HttpEntity
import akka.http.scaladsl.model.MediaTypes.{`application/json`, `text/html`}
import akka.http.scaladsl.server.{RequestContext, RouteResult}

import scala.concurrent.Future

/**
 * convenience methods for returning http responses in certain formats
 */
trait HttpRes {

  def completeAsHtml(req:RequestContext, content:String) : Future[RouteResult] = {
      req.complete(HttpEntity(`text/html`, content))
  }


  def completeAsJson(req:RequestContext, content:String) : Future[RouteResult] = {
    req.complete(HttpEntity(`application/json`, content))
  }
}
