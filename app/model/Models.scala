package model

import com.google.maps.model.LatLng
import org.joda.time.DateTime

/**
  * Created by sarchandran on 9/8/16.
  */

case class AskSuggestion(date: String, city: String, noOfDays: Int, attraction: String, budget: Double)

case class OriginCity(city: String, country: String, latitude: Double, longitude: Double)

case class NearbySearch(latitude: Double, longitude: Double, city: String, radius: Int = 50000,
                        keyword: Option[String], minPrice: Option[Double], maxPrice: Option[Double],
                        openNow: Option[Boolean], locType: Option[String])

case class Directions(originCity: String, destinationCity: String)

case class RouteDistance(summary: String, distance: String, duration: String,
                         durationInTraffic: String, arrivalTime: DateTime, departureTime: DateTime,
                         startAddress: String, endAddress: String, budget: Double)

case class PossibleDestinations(source: LatLng, destinations: Array[LatLng],
                                destinationDetails: Array[Array[RouteDistance]])

case class Attractions(name: String, vicinity: String, rating: Float, photoReference: Option[Array[String]], routeDistance: Array[RouteDistance])
