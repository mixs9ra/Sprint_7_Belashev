package order;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import java.net.HttpURLConnection;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
public class OrderChecks {
    public static final String PATH_ORDERS = "/api/v1/orders";

    @Step("Отправка POST запроса для создания заказа")
    public ValidatableResponse createdOrder(OrderClient orderClient) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .body(orderClient)
                .when()
                .post(PATH_ORDERS)
                .then().log().all();
    }
    @Step("Проверка создания заказа и вывод трек-номера в консоль")
    public void checkOrder(ValidatableResponse createResponse) {
        createResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_CREATED)
                .body("track", notNullValue());
    }
    @Step("Отправка GET запроса для получения списка заказов")
    public void extractedOrderList() {
        given()
                .when()
                .get(PATH_ORDERS)
                .then()
                .statusCode(HttpURLConnection.HTTP_OK)
                .body("orders", notNullValue());
    }
}


