package services.scala

import java.net.InetAddress

import com.maxmind.geoip2.DatabaseReader
import model.{AskSuggestion, SourceCity}
import services.scala.JsonUtil._
import services.scala.AppConstants._

/**
  * Created by sarchandran on 9/8/16.
  */
object ScalaApis {

  def askSuggestion(json: String): String = {
    val askSuggestion = fromJson[AskSuggestion](json)
    getJson[AskSuggestion](askSuggestion)
  }

  def getLocation(ip: String): String = {
    val reader = new DatabaseReader.Builder(geoCity).build()
    val ipAddress = InetAddress.getByName(ip)
    val response = reader.city(ipAddress)
    val loc = response.getLocation
    val sourceCity = SourceCity(response.getCountry.getName, response.getCity.getName, loc.getLatitude, loc.getLongitude)
    getJson[SourceCity](sourceCity)
  }

  def getDistanceRadius(days: Int) = DISTANCE_PER_DAY * days

}
