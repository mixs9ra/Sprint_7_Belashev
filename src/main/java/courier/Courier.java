package courier;

public class Courier {
    private String login;
    private String password;
    private String firstName;


    public Courier() {
    }

    // Параметризованный конструктор
    public Courier(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    // Статический метод для создания предопределенного курьера
    public static Courier createdCourier() {
        // Предопределенный курьер с заданным логином, паролем и именем
        Courier courier = new Courier("DenBel", "4444", "DenBel");
        return courier;
    }

    // Статический метод для создания дубликата курьера (для демонстрации)
    public static Courier duplicateCourier() {
        // Другой курьер с тем же логином, но с другим паролем и именем
        Courier doubleCourier = new Courier("BelDen", "1111", "BelDen");
        return doubleCourier;
    }

    // Статический метод для создания курьера без обязательного поля (для демонстрации)
    public static Courier noRequiredField() {
        // Курьер без логина (null), с заданным паролем и именем
        Courier withoutLogin = new Courier(null, "4444", "DenBel");
        return withoutLogin;
    }

    // Метод для получения логина курьера
    public String getLogin() {
        return login;
    }

    // Метод для установки логина курьера
    public void setLogin(String login) {
        this.login = login;
    }

    // Метод для получения пароля курьера
    public String getPassword() {
        return password;
    }

    // Метод для установки пароля курьера
    public void setPassword(String password) {
        this.password = password;
    }

    // Метод для получения имени курьера
    public String getFirstName() {
        return firstName;
    }

    // Метод для установки имени курьера
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}