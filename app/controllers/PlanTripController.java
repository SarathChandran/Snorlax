package controllers;

import java.time.LocalDate;
import java.util.concurrent.CompletionStage;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import play.libs.ws.WSClient;
import play.mvc.Controller;
import play.mvc.Result;
import util.AppConstants;
import util.ScalaApis;

/**
 * Created by sarchandran on 9/6/16.
 */
public class PlanTripController extends Controller {

    @Inject WSClient ws;

    public CompletionStage<Result> listHolidays() {
        Integer year = LocalDate.now().getYear();
        return ws.url("https://holidayapi.com/v1/holidays").setQueryParameter("key", AppConstants.HOLIDAY_API_KEY())
                .setQueryParameter("country", AppConstants.COUNTRY())
                .setQueryParameter("year", year.toString()).get().thenApply(r -> ok(r.asJson().toString()));
    }

    public Result askSuggestion() {
        JsonNode json = request().body().asJson();
        return ok(ScalaApis.askSuggestion(json.toString()));
    }

    public CompletionStage<Result> getSourceCity() {
        return ws.url("https://api.ipify.org/").setQueryParameter("format", "json").get()
                .thenApply(r -> ok(ScalaApis.getLocation(r.asJson().get("ip").asText())));
    }

    public Result getNearbyPlaces() {
        JsonNode json = request().body().asJson();
        return ok(ScalaApis.getNearbyPlaces(json.toString()));
    }

    public CompletionStage<Result> getNearbyAttractions() throws Exception{
        JsonNode json = request().body().asJson();
        double lat = json.get("latitude").asDouble();
        double lng = json.get("longitude").asDouble();
        return ws.url("https://maps.googleapis.com/maps/api/place/search/json")
                .setQueryParameter("key", AppConstants.GOOGLE_MAPS_API_KEY())
                .setQueryParameter("types", "natural_feature")
                .setQueryParameter("location", lat + "," + lng)
                .setQueryParameter("radius", "50000").get().thenApply(r -> ok(r.asJson().toString()));
    }

    public Result getDirections() {
        JsonNode json = request().body().asJson();
        return ok(ScalaApis.getRouteDistance(json.toString()));
    }

    public Result getPlaceDetails(String placeId) {
        return ok(ScalaApis.getPlaceDetails(placeId));
    }

}