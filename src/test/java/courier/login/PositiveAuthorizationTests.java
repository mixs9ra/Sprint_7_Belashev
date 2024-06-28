package courier.login;

import courier.Courier;
import courier.CourierAuthorization;
import courier.CourierChecks;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotEquals;

public class PositiveAuthorizationTests {

    private final CourierChecks check = new CourierChecks();
    int courierId;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    }

    @After
    public void deleteCourier() {
        // Удаляем курьера, если он был создан
        if (courierId != 0) {
            ValidatableResponse response = check.deleteCourier(courierId);
            check.deleteSuccesfully(response);
        }
    }

    @Test
    @DisplayName("Тест на авторизацию курьера")
    public void testAuthorizationCourier() {
        // Позитивный тест на авторизацию курьера
        var autho = CourierAuthorization.from(Courier.createdCourier());

        // Пытаемся авторизоваться и получить ответ
        ValidatableResponse loginResponse = check.loginCourier(autho);

        // Проверяем успешность авторизации и получаем ID курьера
        courierId = check.authorization(loginResponse);

        // Убеждаемся, что ID курьера не равен 0 (т.е. курьер успешно авторизован)
        assertNotEquals(0, courierId);
    }
}