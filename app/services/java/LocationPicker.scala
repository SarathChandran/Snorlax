package services.java

import com.google.maps.model.LatLng
import model.{PossibleDestinations, RouteDistance}
import util.{AppConstants, ScalaApis}

/**
  * Created by sarchandran on 9/10/16.
  */
object LocationPicker {

  val loc = Map("Chennai" -> 0, "Mumbai" -> 1, "Banglore" -> 2, "Delhi" -> 3, "Shimla" -> 4)

  val distanceMatrix = Array.ofDim[(String, String, Int)](5,5)
  distanceMatrix(0)(0) = ("Chennai", "Chennai", 0)
  distanceMatrix(0)(1) = ("Chennai", "Mumbai", 1337)
  distanceMatrix(0)(2) = ("Chennai", "Banglore", 346)
  distanceMatrix(0)(3) = ("Chennai", "Delhi", 2217)
  distanceMatrix(0)(4) = ("Chennai", "Shimla", 2524)

  distanceMatrix(1)(0) = ("Mumbai", "Chennai", 1337)
  distanceMatrix(1)(1) = ("Mumbai", "Mumbai", 0)
  distanceMatrix(1)(2) = ("Mumbai", "Banglore", 980)
  distanceMatrix(1)(3) = ("Mumbai", "Delhi", 1415)
  distanceMatrix(1)(4) = ("Mumbai", "Shimla", 1764)

  distanceMatrix(2)(0) = ("Banglore", "Chennai", 346)
  distanceMatrix(2)(1) = ("Banglore", "Mumbai", 980)
  distanceMatrix(2)(2) = ("Banglore", "Banglore", 0)
  distanceMatrix(2)(3) = ("Banglore", "Delhi", 2166)
  distanceMatrix(2)(4) = ("Banglore", "Shimla", 2488)

  distanceMatrix(3)(0) = ("Delhi", "Chennai", 2217)
  distanceMatrix(3)(1) = ("Delhi", "Mumbai", 1415)
  distanceMatrix(3)(2) = ("Delhi", "Banglore", 2166)
  distanceMatrix(3)(3) = ("Delhi", "Delhi", 0)
  distanceMatrix(3)(4) = ("Delhi", "Shimla", 350)

  distanceMatrix(4)(0) = ("Shimla", "Chennai", 2524)
  distanceMatrix(4)(1) = ("Shimla", "Mumbai", 1764)
  distanceMatrix(4)(2) = ("Shimla", "Banglore", 2488)
  distanceMatrix(4)(3) = ("Shimla", "Delhi", 350)
  distanceMatrix(4)(4) = ("Shimla", "Shimla", 0)


  def getPossibleLocation(city: String, noOfDays: Int) = {
    val filterDistance:Int = AppConstants.DISTANCE_PER_DAY * noOfDays
    val index = loc(city)
    val locations: Array[(String, String, Int)] = distanceMatrix(index).filter { x => x._3 != 0 && x._3 < filterDistance }
    val source = ScalaApis.getLatLong(city)
    val destinations: Array[LatLng] = locations.map { loc => ScalaApis.getLatLong(loc._2) }
    val destinationDetails : Array[Array[RouteDistance]] = locations.map { case (originCity, destinationCity, distance) =>
        ScalaApis.getDirections(originCity, destinationCity)
    }
    PossibleDestinations(source, destinations, destinationDetails)
  }
}
