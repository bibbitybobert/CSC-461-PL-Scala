package book_robert

import scala.xml._

class Claim extends RDP{
  private var date:String = ""

  override def addData(): Unit = {
    print("Date:> ")
    this.date = myIO.input()
  }

  override def displayData(): String = {
    val spacing: String = " " * 4
    f"\n$spacing%sClaim: $date%s"
  }

  override def readXML(node: Node): Unit = {
    this.date = node.text.strip().split(" ")(0)
  }

  override def writeXML(): Elem = {
    Elem(null, "Claim", Null, TopScope, true,
      Elem(null, "date", Null, TopScope, true, Text(this.date.strip())))
  }
}
