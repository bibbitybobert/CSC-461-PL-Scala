package book_robert

import scala.xml._

abstract class RDP {
  def addData(): Unit
  def displayData(): String
  def readXML(node:Node): Unit
  def writeXML(): Elem
}
