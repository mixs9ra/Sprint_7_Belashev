package courier.login;

import courier.CourierAuthorization;
import courier.CourierChecks;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class NegativeAuthorizationTests {
    private final CourierChecks check = new CourierChecks();
    int courierId;
    String errorCode;

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
    @DisplayName("Негативный тест: неправильный пароль")
    public void testInvalidPassword() {
        // Тест на авторизацию с неправильным паролем
        CourierAuthorization autho = CourierAuthorization.wrongPassword();

        // Пытаемся авторизоваться и ожидаем ошибку
        ValidatableResponse loginResponse = check.loginCourier(autho);
        check.invalidCredentials(loginResponse);
    }


    @Test
    @DisplayName("Негативный тест: отсутствие логина")
    public void testMissingLogin() {
        // Тест на авторизацию без указания логина
        CourierAuthorization autho = CourierAuthorization.missedLogin();

        // Пытаемся авторизоваться без логина и ожидаем ошибку
        ValidatableResponse missedLoginResponse = check.loginCourier(autho);
        check.withoutLogin(missedLoginResponse);
    }


    @Test
    @DisplayName("Негативный тест: авторизация несуществующего пользователя")
    public void testNonExistentUser() {
        // Тест на авторизацию несуществующего пользователя
        CourierAuthorization autho = CourierAuthorization.invalidLogin();

        // Пытаемся авторизоваться под несуществующим логином и ожидаем ошибку неверных учетных данных
        ValidatableResponse invalidLoginResponse = check.loginCourier(autho);
        check.invalidCredentials(invalidLoginResponse);
    }

}