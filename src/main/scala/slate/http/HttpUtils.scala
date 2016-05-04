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

import akka.http.scaladsl.model.HttpRequest


/**
 * Akka Http utilities
 */
object HttpUtils {

  def buildUriParts(req: HttpRequest): String =
  {
    val nl = "\r\n"
    val result = "uri.host     : " + req.getUri().host() + nl +
      "uri.path     : " + req.getUri().path() + nl +
      "uri.port     : " + req.getUri().port() + nl +
      "uri.params   : " + buildParams(req.getUri() ) + nl +
      "uri.query    : " + req.getUri().queryString() + nl +
      "uri.scheme   : " + req.getUri().scheme() + nl +
      "uri.userinfo : " + req.getUri().userInfo() + nl
    result
  }


  def buildParams( uri: akka.http.javadsl.model.Uri ) : String =
  {
    val params = uri.parameterMap()
    if(params.containsKey("name")) return params.get("name")

    "none"
  }
}
