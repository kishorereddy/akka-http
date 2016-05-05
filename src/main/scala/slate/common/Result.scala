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
package slate.common

case class Result(success:Boolean, message:String, data:Option[Any], code:Int) {
}


object Result{

  def apply(success:Boolean, message:String = "", data:Option[Any] = None ):Result = {
    val code = if (success) 1 else 0
    new Result(success, message, data, code)
  }
}
