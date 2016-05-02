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

package com.slate.http.examples

import akka.http.scaladsl.server.{RouteConcatenation, PathMatcher, Route, Directive}
import akka.http.scaladsl.server.directives.{RouteDirectives, PathDirectives}

object ModelPathBuilder extends PathDirectives with RouteDirectives with RouteConcatenation {

  /**
   * builds up a crud path ( create, retrieve, update, delete ) for the model name specified.
   * @param model : "user"
   * @return
   * @note : this builder must implement RouteConcatenation to use the "~"
   */
  def buildPathsForModel(model:String):Route = {
    var paths =  path ( model / "create" ) { complete ( model + " - create") }
    paths = paths.~( path ( model / "retrieve") { complete ( model + " - retrieve") } )
    paths = paths.~( path ( model / "update"  ) { complete ( model + " - update")   } )
    paths = paths.~( path ( model / "delete"  ) { complete ( model + " - delete")   } )
    paths
  }
}
