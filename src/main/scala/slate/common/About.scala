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

/**
 * represent meta-data about an application
 * @param name    : name of app
 * @param desc    : desc of app
 * @param group   : group owning the app
 * @param region  : region associated with app
 * @param url     : url for more information
 * @param contact : contact person(s) for app
 * @param version : version of the app
 * @param tags    : tags describing the app
 */
case class About (
                 name     : String = "",
                 desc     : String = "",
                 group    : String = "",
                 region   : String = "",
                 url      : String = "",
                 contact  : String = "",
                 version  : String = "",
                 tags     : String = ""
            )
{
}



object About
{
  val none = new About(
    name     = "",
    desc     = "",
    group    = "",
    region   = "",
    url      = "",
    contact  = ""
  )
}