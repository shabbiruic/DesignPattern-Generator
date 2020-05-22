package Converters

import net.liftweb.json.JsonAST.JValue


trait JsonConverter {
  def objectToJsonString(obj: Any): String

  def jsonStringToObject(json: String): JValue
}
