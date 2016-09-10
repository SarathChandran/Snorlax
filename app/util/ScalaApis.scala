package util

import java.net.InetAddress

import com.google.maps.model.{LatLng, PlacesSearchResult}
import com.google.maps.{DirectionsApi, PlacesApi}
import com.maxmind.geoip2.DatabaseReader
import model._
import AppConstants._
import JsonUtil._

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
    val sourceCity = OriginCity(response.getCity.getName, response.getCountry.getName, loc.getLatitude, loc.getLongitude)
    getJson[OriginCity](sourceCity)
  }

  def getDistanceRadius(days: Int) = DISTANCE_PER_DAY * days

  def getNearbyPlaces(json: String): String = {
    val nearbyPlaces = fromJson[NearbySearch](json)
    val response = PlacesApi.nearbySearchQuery(googleContext,
      new LatLng(nearbyPlaces.latitude, nearbyPlaces.longitude)).await()
    getJson[Array[PlacesSearchResult]](response.results)
  }

  def getDirection(json: String): String = {
    val directions = fromJson[Directions](json)
    val response = DirectionsApi.getDirections(googleContext,
      directions.originCity, directions.destinationCity).await()
    val routes = response.routes.map { route =>
      val legs = route.legs(0)
      val distance = Option(legs.distance).map(_.humanReadable).getOrElse(null)
      val cost: Long = Option(legs.distance).map(_.inMeters).getOrElse(1l)
      val duration = Option(legs.duration).map(_.humanReadable).getOrElse(null)
      val durationInTraffic = Option(legs.durationInTraffic).map(_.humanReadable).getOrElse(null)
      RouteDistance(route.summary, distance, duration,
        durationInTraffic, legs.arrivalTime, legs.departureTime, legs.startAddress, legs.endAddress, (cost * 12)/1000)
    }
    getJson[Array[RouteDistance]](routes)
  }

}
