package model

import org.joda.time.DateTime

/**
  * Created by sarchandran on 9/8/16.
  */

case class AskSuggestion(date: String, noOfDays: Int, attraction: String, budget: Double)

case class OriginCity(city: String, country: String, latitude: Double, longitude: Double)

case class NearbySearch(latitude: Double, longitude: Double, radius: Int = 50000,
                        keyword: Option[String], minPrice: Option[Double], maxPrice: Option[Double],
                        openNow: Option[Boolean], locType: Option[String])

case class Directions(originCity: String, destinationCity: String)

case class RouteDistance(summary: String, distance: String, duration: String,
                         durationInTraffic: String, arrivalTime: DateTime, departureTime: DateTime,
                         startAddress: String, endAddress: String, budget: Double)
