package book_robert
import scala.collection.mutable
import book_robert.myIO
import book_robert.XMLHelper

import scala.xml.{Elem, Node}

class CarShop extends RDP with CoR{
  var name: String = ""
  private val Services: mutable.ArrayBuffer[Service] = mutable.ArrayBuffer[Service]()

  override def addData(): Unit= {
    print("Name:> ")
    this.name = myIO.input()
    var addServ: Boolean = true
    while addServ do {
      val newServ:Service = new Service
      newServ.addData()
      this.Services += newServ

      print("Add another element (y/n):> ")
      val inStr: String = myIO.input()
      if inStr.toLowerCase.charAt(0) == 'n' then addServ = false
      else if inStr.toLowerCase.charAt(0) == 'y' then addServ = true
      else {
        println("Unknown instruction, assume no new element")
        addServ = false
      }
    }

  }

  override def displayData(): String = {
    var returnString: String = "\n" + ( " " * 2) + "Car Shop: " + name
    for s <- this.Services do {
      returnString = returnString + s.displayData()
    }
    returnString
  }

  override def readXML(node: Node): Unit = {
    this.name = node.attribute("name").getOrElse("").toString
    val children = node.child
    for c <- children do {
      if c.label != "#PCDATA" then {
        val newService: Service = new Service
        newService.readXML(c)
        this.Services += newService
      }
    }
  }

  override def writeXML(): Elem = {
    val attributes: mutable.HashMap[String, String] =
      mutable.HashMap[String, String]("name"-> name)
    val siblings: mutable.Seq[Node] = Services.map(_.writeXML())
    XMLHelper.makeNode("CarShop", attributes, siblings)
  }

  override def findService(serviceNum: String): String = {
    this.Services.map(_.findService(serviceNum))(0)
  }
}
