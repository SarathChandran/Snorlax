package controllers;

import java.time.LocalDate;
import java.util.concurrent.CompletionStage;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import model.AskSuggestion;
import play.libs.ws.WSClient;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Created by sarchandran on 9/6/16.
 */
public class PlanTripController extends Controller {

    @Inject WSClient ws;
    ObjectMapper mapper = new ObjectMapper();

    public static final String COUNTRY = "IN";
    public static final String HOLIDAY_API_KEY = "";

    public CompletionStage<Result> listHolidays() {
        Integer year = LocalDate.now().getYear();
        return ws.url("https://holidayapi.com/v1/holidays").setQueryParameter("key", HOLIDAY_API_KEY).setQueryParameter("country", COUNTRY)
                .setQueryParameter("year", year.toString()).get().thenApply(r -> ok(r.asJson()));
    }

    public Result askSuggestion() throws Exception {
        JsonNode json = request().body().asJson();
        AskSuggestion askSuggestion =  mapper.readValue(json.toString(), AskSuggestion.class);
        return ok(mapper.writeValueAsString(askSuggestion));
    }
}

