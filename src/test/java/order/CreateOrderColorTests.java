package order;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;


@RunWith(Parameterized.class)
public class CreateOrderColorTests {
    private final OrderChecks check = new OrderChecks();
    private String colorBlack;
    private String colorGrey;

    @Before
    public void setUp() {

        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    }

    // Конструктор для параметризации теста
    public CreateOrderColorTests(String colorBlack, String colorGrey) {
        this.colorBlack = colorBlack;
        this.colorGrey = colorGrey;
    }

    // Параметризация теста: определяем различные комбинации цветов заказа
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {"BLACK", null}, // Черный цвет заказа, серый отсутствует
                {null, "GREY"},  // Черный цвет отсутствует, серый цвет заказа
                {"BLACK", "GREY"}, // Оба цвета присутствуют
                {null, null}       // Оба цвета отсутствуют
        });
    }

    @Test
    @DisplayName("Создание заказа")
    public void createOrder(){
        // Создаем список цветов заказа на основе текущей комбинации параметров
        List<String> colors = Arrays.asList(colorBlack, colorGrey);

        // Создаем клиента для заказа с указанными цветами
        OrderClient orderClient = OrderClient.createOrderClient(colors);

        // Выполняем запрос на создание заказа и получаем ответ
        ValidatableResponse createResponse = check.createdOrder(orderClient);

        // Проверяем успешность создания заказа
        check.checkOrder(createResponse);
    }
}