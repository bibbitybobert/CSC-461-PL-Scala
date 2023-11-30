package book_robert

import scala.collection.mutable.ArrayBuffer


import java.io._
import scala.xml.PrettyPrinter
import scala.xml._
import scala.collection.mutable._
import java.text._


class Insurance extends RDP with CoR{
  private val zipCodes: ArrayBuffer[ZipCode] = ArrayBuffer[ZipCode]()
  private var xmlTree: Elem = XMLHelper.makeNode("InsuranceData")

  def removeZip(): Unit = {
    print("What Zip Code:> ")
    val oldZip:String = myIO.input()
    var found:Boolean = false
    var index:Int = 0
    if this.zipCodes.isEmpty then println("Zip Code not found")
    else {
      while !found && index < this.zipCodes.size do{
        if this.zipCodes(index).zip == oldZip then {
          this.zipCodes.remove(index)
          found = true
          println(f"Removed ${oldZip}%s")
        }
        else index += 1
      }
      if !found then println("Zip Code not found")
    }

  }

  override def addData(): Unit = {
    print("What Zip Code:> ")
    val checkZip:String = myIO.input()
    var foundCopy: Boolean = false

    for i <- this.zipCodes do{
      if i.zip == checkZip then foundCopy = true
    }

    if foundCopy then println(f"$checkZip is already in the database")
    else {
      val newZip: ZipCode = new ZipCode
      newZip.zip = checkZip
      newZip.addData()
      this.zipCodes += newZip
    }
  }

  override def displayData(): String = {
    var returnString:String = ""
    for i <- this.zipCodes do {
      returnString = returnString + "Zip Code: " + i.zip + "\n======================================================"
      returnString = returnString + i.displayData()
    }
    returnString
  }

  override def readXML(node: Node): Unit = {
    val children = node.child //list of zipcodes
    for child <- children do {
      if child.label != "#PCDATA" then {
        val newZip: ZipCode = new ZipCode
        newZip.readXML(child)
        this.zipCodes += newZip
      }
    }
  }



  override def writeXML(): Elem= {
    print("file name:> ")
    val fileName:String = myIO.input()

    val children: Seq[Node] = this.zipCodes.map(_.writeXML())

    this.xmlTree = XMLHelper.makeNode("InsuranceData", null, children)

    val prettyPrinter = new scala.xml.PrettyPrinter(80, 2)
    val prettyXml = prettyPrinter.format(this.xmlTree)
    val write= new FileWriter(fileName)
    write.write(prettyXml)
    write.close()

    this.xmlTree
  }

  def findCars(): String = {
    print("Zip Code:> ")
    val zipToSearch:String = myIO.input()
    print("Vehicle:> ")
    val vehicleToFind: String = myIO.input()
    var outString: String = ""

    for i <- this.zipCodes do {
      if i.zip == zipToSearch then {
        outString = outString + i.findCars(vehicleToFind)
      }
    }
    outString
  }

  override def findService(serviceNum: String): String = {

    var outString: String = ""
    outString = this.zipCodes.map(_.findService(serviceNum)).find(_ != "").getOrElse("")
    if outString == "" then outString = f"$serviceNum not found"
    outString
  }

  def getInsurance(zipToSearch: String): String = {
    var outString:String = "Value:"
    val formatter: DecimalFormat = new DecimalFormat("#,##0.00")
    for z <- this.zipCodes do {
      if z.zip == zipToSearch then outString = f"$outString $$${formatter.format(z.getInsurance)}"
    }
    outString
  }

  def getInsuranceFor(zipTolook: String):String = {
    val formatter: DecimalFormat = new DecimalFormat("#,##0.00")
    var outString:String = ""
    print("What Owner:> ")
    val ownerToSearch: String = myIO.input().toLowerCase()
    for z <- this.zipCodes do {
      if z.zip == zipTolook then outString = f"Monthly Payment: $$${formatter.format(z.getInsuranceFor(ownerToSearch))}"
    }
    outString
  }
}
