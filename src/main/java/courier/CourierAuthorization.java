package courier;

public class CourierAuthorization {
    private String login;
    private String password;


    public CourierAuthorization() {
    }

    // Параметризованный конструктор
    public CourierAuthorization(String login, String password) {
        this.login = login;
        this.password = password;
    }

    // Статический метод для создания объекта CourierAuthorization из объекта Courier
    public static CourierAuthorization from(Courier courier){
        return new CourierAuthorization(courier.getLogin(), courier.getPassword());
    }

    // Статический метод для создания объекта с некорректным паролем
    public static CourierAuthorization wrongPassword() {
        CourierAuthorization credentials = new CourierAuthorization("DenBel", "password123");
        return credentials;
    }

    // Статический метод для создания объекта без логина
    public static CourierAuthorization missedLogin() {
        CourierAuthorization autho = new CourierAuthorization(null, "4444");
        return autho;
    }

    // Статический метод для создания объекта с недопустимым логином
    public static CourierAuthorization invalidLogin() {
        CourierAuthorization autho = new CourierAuthorization("DenBel", "4444");
        return autho;
    }

    // Метод для получения логина
    public String getLogin() {
        return login;
    }

    // Метод для установки логина
    public void setLogin(String login) {
        this.login = login;
    }

    // Метод для получения пароля
    public String getPassword() {
        return password;
    }

    // Метод для установки пароля
    public void setPassword(String password) {
        this.password = password;
    }
}