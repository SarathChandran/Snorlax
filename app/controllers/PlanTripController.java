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
                .setQueryParameter("year", year.toString()).get().thenApply(r -> ok(r.asJson()));
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

    public Result getDirections() {
        JsonNode json = request().body().asJson();
        return ok(ScalaApis.getDirection(json.toString()));
    }

}