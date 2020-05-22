package Filters


//trait to define the basic structure of filter
trait ParentFilter{

  def getFilter():String

}

// Parent Class for all the filters
class Filter[A,B](value:A,key:B) extends ParentFilter {

//  method to provide the filter in query form.
    def getFilter(): String =
      value match{
        case i:Int => key+" : " + i
        case s:String => key+" : " + "\\\"" +s+"\\\""
      }

  }







