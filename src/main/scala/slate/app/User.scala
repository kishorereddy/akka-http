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

import spray.json.DefaultJsonProtocol

/**
 * Sample domain class for showing the model / crud api operations
  *
  * @param id
 * @param name
 */
case class User(id: String, name: String) {

}


object JsonImplicits extends DefaultJsonProtocol  {
  implicit val impUser = jsonFormat2(User)
}