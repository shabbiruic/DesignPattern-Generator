package Converters

import CaseClasses.{DataCase, MainCase}
import net.liftweb.json.DefaultFormats

/**
 * A class to convert json string to Scala object and handle the Option feature
 */

case class FilterConverter() {

  //function to convert json string to JValue Scala Object and extract it according to the structure
  def filtercon(client: String) : DataCase = {
    val jsonstr = LiftJson().jsonStringToObject(client)

    implicit val formats = DefaultFormats
    val jsonstring = jsonstr.extract[MainCase]
    //println(jsonstring.toString)
    //val pg = jsonstring.data.user.repositories.totalCount
    jsonstring.data
  }

  // function to handle the Option feature of Scala
  def filter(opt : Option[Any]): String = {
    opt
    match {
      case Some(h) => h.toString
      case _ => "Please specify the value properly!!!"
    }
  }
}
