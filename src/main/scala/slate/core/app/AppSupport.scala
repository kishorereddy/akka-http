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

package slate.core.app

import slate.common.{Reflect, About, Lang, Host}

import scala.collection.mutable.ListBuffer

trait AppSupport {

  protected var _host = Host.none
  protected var _lang = Lang.none
  protected var _about = About.none


  def init():Unit = {
    _host = Host.local()
    _lang = Lang.asScala()
  }


  def about: About = _about
  def host: Host =  _host
  def lang: Lang =  _lang


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
