package courier;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import java.net.HttpURLConnection;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertTrue;

public class CourierChecks {
    public static final String COURIER_PATH = "/api/v1/courier";

    @Step("Отправка POST запроса на создание курьера")
    public ValidatableResponse createCourier(Courier courier) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then().log().all();
    }

    @Step("Проверка успешного создания курьера и вывод статуса в консоль")
    public void successfulCreated(ValidatableResponse createResponse) {
        boolean created = createResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_CREATED)
                .extract()
                .path("ok");

        assertTrue(created);
    }

    @Step("Проверка ответа на конфликт при создании двух одинаковых курьеров")
    public void conflictCourier(ValidatableResponse conflictResponse) {
        conflictResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_CONFLICT);
    }

    @Step("Проверка ответа на неверные учётные данные")
    public void invalidCredentials(ValidatableResponse notFoundResponse) {
        notFoundResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_NOT_FOUND);
    }

    @Step("Проверка ответа на запрос без авторизации")
    public void withoutLogin(ValidatableResponse badRequestResponse) {
        badRequestResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_BAD_REQUEST);
    }

    @Step("Авторизация курьера")
    public ValidatableResponse loginCourier(CourierAuthorization autho) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .body(autho)
                .when()
                .post(COURIER_PATH + "/login")
                .then().log().all();
    }

    @Step("Получение идентификатора курьера после авторизации")
    public int authorization(ValidatableResponse loginResponse) {
        int id =  loginResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .extract()
                .path("id");
        return id;
    }

    @Step("Удаление курьера по идентификатору")
    public ValidatableResponse deleteCourier(int id) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .body(Map.of("id", id))
                .when()
                .delete(COURIER_PATH + "/" + id)
                .then().log().all()
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK);
    }


    public void deleteSuccesfully(ValidatableResponse response) {
    }
}