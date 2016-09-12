package util

import java.io.File

import com.google.maps.GeoApiContext
import com.google.maps.model.PlaceType
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
  val googleContext = new GeoApiContext().setApiKey(GOOGLE_MAPS_API_KEY)
  import PlaceType._
  val attactionTypes = Seq(AMUSEMENT_PARK,AQUARIUM, ART_GALLERY, CAMPGROUND, CASINO, CHURCH, HINDU_TEMPLE, MUSEUM, NIGHT_CLUB, PARK, PLACE_OF_WORSHIP, SHOPPING_MALL, ZOO)
}
