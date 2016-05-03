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

package slate.web.core

import akka.http.scaladsl.server.{RouteResult, RequestContext}

import scala.concurrent.Future

object Auth {

  val password = "ABC123"


  def authorize(ctx:RequestContext, callback:(RequestContext) => Future[RouteResult]): Future[RouteResult] =
  {
    val req = ctx.request
    val header = req.getHeader("user-token")

    // Missing header ?
    if (!header.isDefined)
      return ctx.complete("Unauthoried")

    // Missing value ?
    val value = header.get.value()
    if(value == null || value == "")
      return ctx.complete("Unauthorized")

    if(value != password)
      return ctx.complete("Unauthorized")

    callback(ctx)
  }
}
