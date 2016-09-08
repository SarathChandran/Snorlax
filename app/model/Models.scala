package model

/**
  * Created by sarchandran on 9/8/16.
  */

case class AskSuggestion(date: String, noOfDays: Int, attraction: String, budget: Double)
case class SourceCity(city: String, country: String, lat: Double, long: Double)
