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

package slate.common

import scala.collection.mutable.ListBuffer

trait AppSupport {

  protected var _host = Host.none
  protected var _lang = Lang.none
  protected var _about = About.none


  /**
   * initialize the app.
   * derived classes can override this to populate the about info.
   */
  def init():Unit = {
    _host = Host.local()
    _lang = Lang.asScala()
  }


  /**
   * info about this app
   * @return
   */
  def about: About = _about


  /**
   * info about the host computer hosting this app
   * @return
   */
  def host: Host =  _host


  /**
   * info about the language and version used to run this app
   * @return
   */
  def lang: Lang =  _lang


  /**
   * builds a list of properties fully describing this app by adding
   * all the properties from the about, host and lang fields.
   * @return
   */
  def info() : List[(String,Any)] = {
    val items = new ListBuffer[(String,Any)]

    items.append(("ABOUT", "==================================="))
    for((k,v) <- Reflect.getFields(about)) { items.append((k,v)) }

    items.append(("HOST", "==================================="))
    for((k,v) <- Reflect.getFields(host)) { items.append((k,v)) }

    items.append(("LANG", "==================================="))
    for((k,v) <- Reflect.getFields(lang)) { items.append((k,v)) }

    items.toList
  }
}
