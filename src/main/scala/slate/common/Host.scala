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
 * Represents a host such as a cloud server. e.g. ec2
 * @param name    : name of host e.g. srv-001
 * @param ip      : ip address
 * @param origin  : origin of the server e.g. aws | azure
 * @param arch    : architecture of the server e.g. linux | windows
 * @param version : version of the server e.g. linux version or windows version
 * @param ext1    : additional information about the server
 */
case class Host(
                  name     : String = "",
                  ip       : String = "",
                  origin   : String = "",
                  arch     : String = "",
                  version  : String = "",
                  ext1     : String = ""
               )
{
}



object Host
{
  val none = new Host(
    name     = "none",
    ip       = "-",
    origin   = "local",
    arch     = "-",
    version  = "-",
    ext1     = "-"
  )


  def local(): Host =
  {
    new Host(
      name     = getComputerName,
      ip       = System.getProperty("os.name"),
      origin   = "local",
      arch     = System.getProperty("os.arch"),
      version  = System.getProperty("os.version"),
      ext1     = scala.util.Properties.tmpDir
    )
  }


  def getComputerName: String =
  {
    val env = System.getenv()

    if (env.containsKey("COMPUTERNAME"))
      return env.get("COMPUTERNAME");

    else if (env.containsKey("HOSTNAME"))
      return env.get("HOSTNAME");

    "Unknown Computer";
  }
}
