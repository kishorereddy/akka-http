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

import slate.common.{About, Lang, Host}

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
}
