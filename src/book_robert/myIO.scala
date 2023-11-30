package book_robert

import scala.io.StdIn

object myIO:
  def input(): String = {
      var inStr:String = StdIn.readLine()

      while (inStr.isEmpty)
        inStr = StdIn.readLine()

      inStr
  }

