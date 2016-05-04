/**
  * <slate_header>
  * author: Kishore Reddy
  * url: https://github.com/kishorereddy/scala-slate
  * copyright: 2016 Kishore Reddy
  * license: https://github.com/kishorereddy/scala-slate/blob/master/LICENSE.md
  * desc: a scala micro-framework
  * usage: Please refer to license on github for more info.
  * </slate_header>
  */
package slate.http

import slate.app.User
import spray.json.DefaultJsonProtocol

// akkaStreamVersion

trait HttpJson extends DefaultJsonProtocol {
  implicit val userFormat = jsonFormat2(User.apply)
}
