package book_robert
import book_robert.myIO

import scala.collection.mutable
import scala.xml._

class Owner extends RDP{
  var name: String = ""
  var vehicles: mutable.ArrayBuffer[Vehicle] = mutable.ArrayBuffer[Vehicle]()
  var claims: mutable.ArrayBuffer[Claim] = mutable.ArrayBuffer[Claim]()

  override def addData(): Unit = {
    print("Name:> ")
    this.name = myIO.input().toLowerCase

    var addNewElem: Boolean = true
    while addNewElem do {
      print("\nWhat Element (Vehicle, Claim):> ")
      var inStr:String = myIO.input()

      if inStr.toLowerCase().charAt(0) == 'v' then{
        val newVehicle: Vehicle = new Vehicle
        newVehicle.addData()
        this.vehicles += newVehicle
        println("Added Vehicle")
      }
      else if inStr.toLowerCase().charAt(0) == 'c' then{
        val newClaim: Claim = new Claim
        newClaim.addData()
        this.claims += newClaim
        println("Added Claim")
      }
      print("Add another Owner element (y/n):> ")
      inStr = myIO.input()

      if inStr.toLowerCase().charAt(0) == 'y' then addNewElem = true
      else if inStr.toLowerCase.charAt(0) == 'n' then addNewElem = false
      else {
        print("Unknown option, assuming no new element")
        addNewElem = false
      }
    }

  }

  override def displayData(): String = {
    var returnString:String = "\n" + (" " * 2) + this.name
    returnString  = returnString + "\n" + (" " * 2) + "Vehicle(s)"
    for v <- this.vehicles do {
      returnString = returnString + v.displayData()
    }
    returnString = returnString + "\n" + (" " * 2) + "Claim(s)"
    for c <- this.claims do {
      returnString = returnString + c.displayData()
    }
    returnString
  }

  override def readXML(node: Node): Unit = {
    this.name = node.attribute("name").getOrElse("").toString.toLowerCase()
    val children = node.child
    for c <- children do {
      if c.label != "#PCDATA" then {
        if c.label == "Vehicle" then {
          val newVehicle: Vehicle = new Vehicle
          newVehicle.readXML(c)
          this.vehicles += newVehicle
        }
        else if c.label == "Claim" then {
          val newClaim: Claim = new Claim
          newClaim.readXML(c)
          this.claims += newClaim
        }
      }
    }
  }

  override def writeXML(): Elem = {
    val attributes: mutable.HashMap[String, String] =
      mutable.HashMap[String, String]("name"-> name)

    val siblings: Seq[Node] = vehicles.map(_.writeXML()) ++ claims.map(_.writeXML())
    XMLHelper.makeNode("Owner", attributes, siblings)
  }

  def findCars(car: String): String = {
    var outString: String = ""
    for v <- this.vehicles do {
      if v.make == car then outString = outString + '\n' + v.displayData().strip()
    }
    outString
  }

  def getInsurance: Double = {
    var outVal:Double = 0.0
    for i <- this.vehicles do {
      outVal = outVal + i.value.toDoubleOption.getOrElse(0.0)
    }
    outVal
  }

}
