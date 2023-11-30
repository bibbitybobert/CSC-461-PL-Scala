package book_robert

import java.io._
import scala.xml.*


/*
    Every Line with a * has its grading tag (if reached): _YES_

    0. Got it running	6			_Yes_

    1.	Add + Display*	36
    Prompts correct 				_YES_
    Adds each item 					_YES_
      Above displays correctly formatted 		_YES_


    2A) Remove + Display*	10
    Prompts correct					_YES_
    Removes and displays correctly 			_YES_


    2B) Add + XML save*	14
    Console added items saved correctly 		_YES_
      Console added multiples is saved correctly 	_YES_


    2C) XML load + XML save*	14
    1 XML file loaded and saved correctly 		_YES_
      2+ XML file loaded and saved correctly		_YES_


    2D) XML load + Display*	12
    1 XML file loaded and displays correctly 	_YES_
      2+ XML file loaded and displays correctly	_YES_


    2E) XML+ Display with bad file handing	10
    All errors handled 				_YES_


    3.	Stress test for above*	12
      Loads in file, adds data, and displays/saves correctly		_YES_
      Appends a file and displays/saves correctly 			_YES_
    Removes elements after edits, and displays/saves correctly 	_YES_


    4. Find cars*	9
    RDP format at least there				_YES_
      Lists cars						_YES_
    Formatting						_YES_
      Handles “not found case”				_YES_


    5. Find service*	14
    CoR format at least there				_YES_
      First item found and output formatted correctly		_YES_
      Handles “not found case”				_YES_


    6a.  Total Insured 	7				_YES_
      Correct with no claims					_YES_
    Correct with claims 					_YES_
      Parallelized* 						_YES_


    6a.  Total Insured 	9				_YES_
      Correct with no claims					_YES_
    Correct with claims 					_YES_
      Parallelized* 						_YES_
*/

object MainStarter {
    private var manager: Insurance = new Insurance

    private def addData(): Unit = {
        // GRADING: ADD
        manager.addData()
    }

    private def displayData(): Unit = {
        // GRADING: PRINT
        println(manager.displayData())
    }

    private def removeZip(): Unit = {
        manager.removeZip()
    }

    private def loadXML(): Unit = {
        try{
            print("File name:> ")
            val fileName:String = myIO.input()
            val topNode:Node = XML.loadFile(fileName)
            if topNode.label != "InsuranceData" then throw new IllegalAccessException()
            // GRADING: READ
            manager.readXML(topNode)
        }
        catch {
            case e: FileNotFoundException =>
                println(f"Could not open file: ${e.getMessage}")
            case e: IllegalAccessException =>
                println(f"Invalid XML file. Needs to be an InsuranceData XML file")
        }

    }

    private def writeXML(): Unit = {
        // GRADING: WRITE
        manager.writeXML()
    }

    private def findCars(): Unit = {
        // GRADING: VEHICLE
        println(manager.findCars())
    }

    private def findService(): Unit = {
        print("Car Service:> ")
        val serviceNum:String = myIO.input()
        // GRADING: SERVICE
        println(manager.findService(serviceNum))
    }

    private def insuranceTotal(): Unit = {
        print("What Zip Code:> ")
        val zipToSearch:String = myIO.input()
        // GRADING: PARALLEL
        println(manager.getInsurance(zipToSearch))
    }

    private def insuranceFor(): Unit = {
        print("What Zip Code:> ")
        val zip:String = myIO.input()
        // GRADING: INSURANCE
        println(manager.getInsuranceFor(zip))
    }

    def main(args: Array[String]): Unit = {
        val menu: String =
            """
              |1) Add Data
              |2) Display Data
              |3) Remove Zip
              |4) Load XML
              |5) Write XML
              |6) Find a Cars of Make in Zip
              |7) Find a Service
              |8) Total Value Insured
              |9) Insurance For
              |0) Quit
              |
              |Choice:> """.stripMargin
        var choice: Any = -1
        var temp:String = ""

        while (choice != "0") {
            print(menu)

            //something to strip out empty lines
            temp = myIO.input()
            choice = temp
            choice match {
                case "1" =>
                    addData()
                case "2" =>
                    displayData()
                case "3" =>
                    removeZip()
                case "4" =>
                    loadXML()
                case "5" =>
                    writeXML()
                case "6" =>
                    findCars()
                case "7" =>
                    findService()
                case "8" =>
                    insuranceTotal()
                case "9" =>
                    insuranceFor()
                case "0" =>
                    manager = new Insurance
                case _ =>
                    println("Element format not found")
            }
        }
    }
}

