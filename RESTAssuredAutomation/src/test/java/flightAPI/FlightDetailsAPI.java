package flightAPI;

import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.Assert;

import java.util.List;

public class FlightDetailsAPI {

    public static void main (String[] args) {

        //Extract the response
        Response resp = given()
                .when()
                .header("Content-Type", "application/json").contentType(ContentType.JSON).accept(ContentType.JSON)
                .get("http://api.aviationstack.com/v1/flights?access_key=768f8d50f891f5fd0470880911a0722f")
                .then()
                .statusCode(200)
                .log().all()
                .extract()
                .response();

        //convert response string to the Json object  using json path
        // JsonPath path = new JsonPath(resp.asString());
        JsonPath path = resp.jsonPath();

        int size = path.getInt("data.size()");
        System.out.println("size = " + size);

        String flightNumber = "123";
        for (int i = 0; i < size; i++) {
            String fNumber = path.getString("data[" + i + "].flight.number");

            if (fNumber.equals(flightNumber)){
                System.out.println(" Flight found ");

                List<Object> departureCity = path.getList("data[" + i + "].departure.airport");
                List<Object> arrivalCity = path.getList("data[" + i + "].arrival.airport");
                List<Object> flight_date = path.getList("data[" + i + "].flight_date");
                System.out.println("Values for departureCity : " + departureCity);
                System.out.println("Values for arrivalCity : " + arrivalCity);
                System.out.println("Values for flight_date : " + flight_date);
                break;
            }
            else {
                System.out.println(" Flight not found ");
            }

        }
        //Assert or validate the request status code
        Assert.assertEquals((resp.statusCode()),200);

        System.out.println(resp.statusCode());
        // System.out.println(resp.asString());
        System.out.println(resp.getBody().asString());
        System.out.println(resp.statusLine());

    }

}
