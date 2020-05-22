package Converters

import net.liftweb.json._
import net.liftweb.json.Serialization.write
import net.liftweb.json.{Formats, parse}

case class LiftJson() extends JsonConverter {
  override def objectToJsonString(obj: Any): String = {
    implicit val formats = DefaultFormats
    val jsonString = write(obj)
    jsonString
  }

  override def jsonStringToObject(json: String): JValue = {
    parse(json)
  }


}
