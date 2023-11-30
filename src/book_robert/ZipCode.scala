package book_robert
import scala.collection.mutable.ArrayBuffer

import scala.xml._
import scala.collection.mutable
import scala.collection.parallel.CollectionConverters.MutableArrayBufferIsParallelizable

class ZipCode extends RDP with CoR{
  private val carShops:  mutable.ArrayBuffer[CarShop] = ArrayBuffer[CarShop]()
  private val owners: mutable.ArrayBuffer[Owner] = ArrayBuffer[Owner]()
  var zip: String = ""

  override def addData(): Unit = {

    var addElement: Boolean = true

    while addElement do{ // add element
      print("\nWhat Element (Owner, Car Shop ):> ")
      var inStr: String = myIO.input()

      if inStr.toLowerCase().charAt(0) == 'o' then{ // add new owner
        this.owners += addOwner()
        println("Added Owner")
      }
      else if inStr.toLowerCase().charAt(0) == 'c' then{ //add new car shop
        this.carShops += addCarShop()
        println("Added Car Shop")
      }
      else { // unknown option
        println("Unknown Option, Exiting")
        addElement = false
      }

      print("Add another Zip Code element (y/n):> ")
      inStr = myIO.input()
      if inStr.toLowerCase.charAt(0) == 'y' then addElement = true
      else if inStr.toLowerCase.charAt(0) == 'n' then addElement = false
      else{
        println("Unknown argument, assuming no new element")
        addElement = false
      }

    }
  }

  private def addOwner(): Owner = {
    val newOwner: Owner = new Owner
    newOwner.addData()
    newOwner
  }

  private def addCarShop(): CarShop = {
    val newCarShop: CarShop = new CarShop
    newCarShop.addData()
    newCarShop
  }

  override def displayData(): String = {
    var returnString: String = ""
    for o <- owners do {
      returnString = returnString + "\n" + (" " * 2) +  "*****************************************************"
      returnString = returnString + o.displayData()
      returnString = returnString + "\n" + (" " * 2) + "*****************************************************\n"
    }
    for c <- carShops do {
      returnString = returnString + "\n" + (" " * 2) +"....................................................."
      returnString = returnString + c.displayData()
      returnString = returnString + "\n" + (" " * 2) + ".....................................................\n"
    }
    returnString
  }

  override def readXML(node:Node): Unit = {
    val children = node.child
    this.zip = node.attribute("code").get.toString()
    for c <- children do {
      if c.label != "#PCDATA" then {
        if c.label == "Owner" then {
          val newOwner: Owner = new Owner
          newOwner.readXML(c)
          this.owners += newOwner
        }
        else if c.label == "CarShop" then {
          val newShop: CarShop = new CarShop
          newShop.readXML(c)
          this.carShops += newShop
        }
      }
    }
  }


  override def writeXML(): Elem = {
    val attributes: mutable.HashMap[String, String] =
      mutable.HashMap[String, String]("code"->zip)
    val children: Seq[Node] = owners.map(_.writeXML()) ++ carShops.map(_.writeXML())
    XMLHelper.makeNode("ZipCode", attributes, children)
  }


  def findCars(car: String):String = {
    var outString: String = ""
    for i <- this.owners do {
      outString = outString + i.findCars(car)
    }
    outString
  }

  override def findService(serviceNum: String): String = {
    var outString:String = ""
    val foundService:String = this.carShops.map(_.findService(serviceNum)).find(_ == serviceNum).getOrElse("")
    if foundService != "" then outString = f"$foundService found in ${this.zip}"
    else outString = ""
    outString
  }

  def getInsurance: Double ={
    val out: Double = this.owners.par.map(_.getInsurance).sum
    out
  }

  def getInsuranceFor(owner: String): Double = {
    val correctOwner:Owner = this.owners.par.find(_.name == owner).get
    val carsTotalValue:Double = correctOwner.getInsurance
    val vehicleCount: Int = correctOwner.vehicles.size
    val claimCount: Int = correctOwner.claims.size
    carsTotalValue * 0.001 + vehicleCount * 25 + claimCount * carsTotalValue * 0.002
  }

}
