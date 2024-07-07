package courier.create;

import courier.Courier;
import courier.CourierAuthorization;
import courier.CourierChecks;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class NegativeCourierTests {
    private final CourierChecks check = new CourierChecks();
    int courierId;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    }

    @After
    public void deleteCourier() {
        // Удаляем курьера(courierId не равен 0)
        if (courierId != 0) {
            ValidatableResponse response = check.deleteCourier(courierId);
            check.deleteSuccesfully(response);
        }
    }

    @Test
    @DisplayName("Негативный тест: создание двух одинаковых курьеров")
    public void testDuplicateCouriers() {
        // Тест на создание двух одинаковых курьеров
        Courier courier = Courier.createdCourier();

        // Создаем первого курьера
        ValidatableResponse createResponse = check.createCourier(courier);
        check.successfulCreated(createResponse);

        // Пробуем создать второго курьера
        ValidatableResponse duplicateResponse = check.createCourier(courier);
        check.conflictCourier(duplicateResponse);

        // Авторизуемся, чтобы получить ID курьера для последующего удаления
        ValidatableResponse loginResponse = check.loginCourier(CourierAuthorization.from(courier));
        courierId = check.authorization(loginResponse);
    }

    @Test
    @DisplayName("Негативный тест: если одного из обязательных полей нет")
    public void testMissingFields() {
        // Тест на создание курьера без обязательного поля
        Courier withoutLogin = Courier.noRequiredField();

        // Пытаемся создать курьера без обязательного поля, ожидаем ошибку
        ValidatableResponse errorResponse = check.createCourier(withoutLogin);
        check.withoutLogin(errorResponse);
    }

    @Test
    @DisplayName("Негативный тест: создание пользователя с логином, который уже есть")
    public void testDuplicateLogin() {
        // Тест на создание курьера с логином, который уже занят другим пользователем
        Courier courier = Courier.createdCourier();
        Courier doubleCourier = Courier.duplicateCourier();

        // Создаем первого курьера
        ValidatableResponse createResponse = check.createCourier(courier);
        check.successfulCreated(createResponse);

        // Пытаемся создать второго курьера с уже занятым логином, ожидаем конфликт
        ValidatableResponse duplicateResponse = check.createCourier(doubleCourier);
        check.conflictCourier(duplicateResponse);

        // Авторизуемся для получения ID первого курьера для последующего удаления
        ValidatableResponse loginResponse = check.loginCourier(CourierAuthorization.from(courier));
        courierId = check.authorization(loginResponse);
    }
}