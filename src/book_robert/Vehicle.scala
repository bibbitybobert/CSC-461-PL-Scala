package book_robert
import scala.xml._
import scala.collection.mutable

class Vehicle extends RDP{
  var make:String = ""
  private var model:String = ""
  private var year:String = ""
  var value:String = ""

  override def addData(): Unit = {
    print("Make:> ")
    this.make = myIO.input()
    print("Model:> ")
    this.model = myIO.input()
    print("Year:> ")
    this.year = myIO.input()
    print("Value:> ")
    this.value = myIO.input()
  }

  override def displayData(): String = {
    val spacing:String = " " * 4
    f"\n$spacing%sVehicle: Make: $make%-10sModel: $model%-10sYear: $year%-10sValue: $$$value%-10s"
  }

  override def readXML(node: Node): Unit = {
    this.make = node.attribute("make").getOrElse("").toString
    this.model = node.attribute("model").getOrElse("").toString
    this.year = node.attribute("year").getOrElse("0").toString
    this.value = node.attribute("value").getOrElse("0").toString
  }

  override def writeXML(): Elem = {
    val attributes: mutable.HashMap[String, String] =
      mutable.HashMap("make"->make, "model"->model, "year"->year, "value"->value)
    XMLHelper.makeNode("Vehicle", attributes)
  }
}
