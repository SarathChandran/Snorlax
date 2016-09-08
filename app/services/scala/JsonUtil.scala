package services.scala

import java.io.StringWriter

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule

import scala.reflect._

/**
  * Created by sarchandran on 6/7/16.
  */
object JsonUtil {

  val mapper = new ObjectMapper()
  mapper.registerModule(DefaultScalaModule)

  def getJson[T](data: T): String = {
    val out = new StringWriter()
    mapper.writeValue(out, data)
    out.toString
  }

  def fromJson[T: ClassTag](json: String): T = {
    mapper.readValue(json, classTag[T].runtimeClass).asInstanceOf[T]
  }
}
