package util

import java.net.InetAddress
import java.text.SimpleDateFormat
import java.util
import java.util.Date

import com.fasterxml.jackson.databind.JsonNode
import com.google.maps.model._
import com.google.maps._
import com.maxmind.geoip2.DatabaseReader
import _root_.model._
import _root_.util.AppConstants._
import _root_.util.JsonUtil._
import services.java.LocationPicker

import scala.util.Try

/**
  * Created by sarchandran on 9/8/16.
  */
object ScalaApis {

  val sdf = new SimpleDateFormat("yyyy-MM-dd")

  def askSuggestion(json: String): String = {
    val askSuggestion = fromJson[AskSuggestion](json)
    val possibleLocation = LocationPicker.getPossibleLocation(askSuggestion.city, askSuggestion.noOfDays)
    getJson[PossibleDestinations](possibleLocation)
  }

  def getLocation(ip: String): String = {
    val reader = new DatabaseReader.Builder(geoCity).build()
    val ipAddress = InetAddress.getByName(ip)
    val response = reader.city(ipAddress)
    val loc = response.getLocation
    val sourceCity = OriginCity(response.getCity.getName, response.getCountry.getName, loc.getLatitude, loc.getLongitude)
    getJson[OriginCity](sourceCity)
  }

  def getDistanceRadius(days: Int) = DISTANCE_PER_DAY * days

  def getNearbyPlaces(json: String): String = {
    val nearbyPlaces = fromJson[NearbySearch](json)
    val response = PlacesApi.nearbySearchQuery(googleContext,
      new LatLng(nearbyPlaces.latitude, nearbyPlaces.longitude)).keyword("attraction").await()
    getJson[Array[PlacesSearchResult]](response.results)
  }

  def getRouteDistance(json: String): String = {
    val directions = fromJson[Directions](json)
    val routes = getDirections(directions.originCity, directions.destinationCity)
    getJson[Array[RouteDistance]](routes)
  }

  def getDirections(originCity: String, destinationCity: String): Array[RouteDistance] = {
    val response = DirectionsApi.getDirections(googleContext,
      originCity,destinationCity).await()
    response.routes.map { route =>
      val legs = route.legs(0)
      val distance = Option(legs.distance).map(_.humanReadable).getOrElse(null)
      val cost: Long = Option(legs.distance).map(_.inMeters).getOrElse(1l)
      val duration = Option(legs.duration).map(_.humanReadable).getOrElse(null)
      val durationInTraffic = Option(legs.durationInTraffic).map(_.humanReadable).getOrElse(null)
      RouteDistance(route.summary, distance, duration,
        durationInTraffic, legs.arrivalTime, legs.departureTime, legs.startAddress, legs.endAddress, (cost * 12)/1000)
    }
  }

  def getLatLong(city: String): LatLng = {
    val response = GeocodingApi.geocode(googleContext, city).await()
    response(0).geometry.location
  }

  def getPlaceDetails(placeId: String) : String = {
    val response = PlacesApi.placeDetails(googleContext, placeId).await()
    getJson[PlaceDetails](response)
  }

  def pullDates(json: JsonNode) : util.List[Date] = {
    import collection.JavaConversions._
    import collection.JavaConverters._
    json.get("holidays").iterator().map { node =>
      val date = node.get(0).get("date").asText()
      sdf.parse(date);
    }.toList.asJava
  }

}
