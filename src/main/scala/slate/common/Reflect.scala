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

import scala.reflect.runtime.{universe => ru}
import ru._

object Reflect {


  def getFields(cc: AnyRef) :Map[String,Any]=
    (Map[String, Any]() /: cc.getClass.getDeclaredFields) {(a, f) => f.setAccessible(true)
     a + (f.getName -> f.get(cc))
  }

  def getFields2(cc:AnyRef): String =
  {
    val info = getFields(cc)
    var text = ""
    for ((k,v) <- info)
    {
      text = text + k + ": " + ( if ( v == null ) "" else v.toString) + "\r\n\r\n"
    }
    text
  }

  def asHtmlTable(cc:AnyRef): String =
  {
    val info = getFields(cc)
    asHtmlTable(info)
  }


  def asHtmlTable(items:Map[String,Any]*): String =
  {
    var text = "<table>"
    for(item <- items)
    {
      for ((k,v) <- item)
      {
        text = text + "<tr>"
        text = text + "<td>" + k + "</td><td>" + ( if ( v == null ) "" else v.toString) + "</td>"
        text = text + "<tr>"
      }
    }

    text = text + "</table>"
    text
  }

  def asHtmlTable(items:List[(String,Any)]): String =
  {
    var text = "<table>"
    for(item <- items)
    {
        text = text + "<tr>"
        text = text + "<td>" + item._1 + "</td><td>" + ( if ( item._2 == null ) "" else item._2.toString) + "</td>"
        text = text + "<tr>"
    }

    text = text + "</table>"
    text
  }


  def asJson(items:List[(String,Any)]): String =
  {
    var text = "{"
    for(i <- 0 until items.size)
    {
      if( i > 0 ) text = text + ","

      val item = items(i)
      text = text + "\"" + item._1 + "\":"
      text = text + "\"" + ( if ( item._2 == null ) "" else item._2.toString.replaceAllLiterally("\"", "\\\"").replaceAllLiterally("\\", "\\\\")) + "\""

    }


    text = text + "}"
    text
  }


  def getFields(item:Any, tpe:Type): String =
  {
    var info = ""

    for(mem <- tpe.members)
    {
      // Method + Public + declared in type
      //println(mem.fullName, mem.isMethod, mem.isTerm)

      if(mem.isPublic && !mem.isMethod && mem.isTerm && mem.owner == tpe.typeSymbol)
      {
        info = info + "\r\n" + mem.fullName
        info = info + "\r\n" + mem.name + ": " + getFieldValue(item, mem.name.toString)
      }
    }
    info
  }


  /**
   * Gets a field value from the instance
   * @param item: The instance to get the field value from
   * @param fieldName: The name of the field to get
   * @return
   */
  def getFieldValue(item:Any, fieldName:String) : Any =
  {
    val m = ru.runtimeMirror(getClass.getClassLoader)
    val im = m.reflect(item)
    val clsSym = im.symbol
    val tpe = clsSym.selfType
    val fieldX = tpe.decl(ru.TermName(fieldName)).asTerm.accessed.asTerm
    val fmX = im.reflectField(fieldX)
    val result = fmX.get
    result
  }
}
