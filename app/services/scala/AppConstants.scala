package services.scala

import java.io.File

import play.Play

/**
  * Created by sarchandran on 9/8/16.
  */
object AppConstants {
  val geoCity: File = new File(Play.application().classloader().getResource("GeoLite2-City.mmdb").getFile)
  val COUNTRY: String = "IN"
  val HOLIDAY_API_KEY: String = ""
  val GOOGLE_MAPS_API_KEY: String = ""
  val DISTANCE_PER_DAY = 200
}
