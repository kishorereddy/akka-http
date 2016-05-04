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

package slate.app

import akka.http.scaladsl.server.{RequestContext, RouteResult}
import scala.concurrent.Future


object Auth {

  val password = "ABC123"


  /**
   * simple authorization mechanism for this example app
   * @param ctx      : The Akka http request context
   * @param callback : Callback that build the Future result to return
   * @return
   */
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
