package book_robert
import scala.xml._


class Service extends RDP with CoR{
  var code:String = ""
  private var description:String = ""

  override def addData(): Unit = {
    print("Code:> ")
    this.code = myIO.input()
    print("Description:> ")
    this.description = myIO.input()
  }

  override def displayData(): String = {
    val spacing: String = " " * 4
    f"\n$spacing%s(${this.code}) ${this.description}"
  }

  override def readXML(node: Node): Unit = {
    this.code = node.attribute("code").get.toString()
    this.description = node.text
  }

  override def writeXML(): Elem = {
    val attr: UnprefixedAttribute = new UnprefixedAttribute("code", this.code, Null)
    Elem(null, "CarService", attr, TopScope, true, Text(this.description))
  }

  override def findService(serviceNum: String): String = {
    var outString:String = ""
    if this.code == serviceNum then outString = this.code
    outString
  }
}
