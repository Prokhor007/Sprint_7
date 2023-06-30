import com.example.courier.CourierClient;
import com.example.courier.CourierCreate;
import com.example.courier.CourierGenerator;
import com.example.courier.CourierLogin;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LoginTest {
    private CourierCreate courier;
    private CourierClient courierClient;
    private CourierLogin courierCredsWithoutLogin;
    private CourierLogin courierCredsWithoutPassword;
    private CourierLogin courierIncorrectCreds;
    private int idCourier;

    @Before
    public void setUp() {
        courier = CourierGenerator.fullFieldsCourier();
        courierClient = new CourierClient();
        courierCredsWithoutLogin = new CourierLogin(courier.getLogin(), "");
        courierCredsWithoutPassword = new CourierLogin("", courier.getPassword());
        courierIncorrectCreds = CourierLogin.from(CourierGenerator.incorrectCourier());
    }

    @After
    public void cleanUp(){
        if (idCourier > 0)
            courierClient.delete(idCourier);
    }

    @Test
    @DisplayName("Успешная авторизация курьера")
    @Description("Проверяем успешный вход в систему с помощью логина и пароля")
    public  void courierLogin(){
        courierClient.create(courier);
        ValidatableResponse loginResponse = courierClient.login(CourierLogin.from(courier));
        idCourier = loginResponse.extract().path("id");
        int statusCode = loginResponse.extract().statusCode();
        assertEquals(200, statusCode);
    }

    @Test
    @DisplayName("Авторизации без указания логина")
    @Description("Проверяем невозможность входа в систему без указания логина")
    public  void  courierCredsWithoutLogin(){
        courierClient.create(courier);
        ValidatableResponse loginResponse = courierClient.login(courierCredsWithoutLogin);
        int statusCode = loginResponse.extract().statusCode();
        String messageResponse = loginResponse.extract().path("message");
        assertEquals(400, statusCode);
        assertEquals("Недостаточно данных для входа", messageResponse);
    }

    @Test
    @DisplayName("Авторизации без указания пароля")
    @Description("Проверяем невозможность входа в систему без указания пароля")
    public  void  courierCredesWithoutPassword(){
        courierClient.create(courier);
        ValidatableResponse loginResponse = courierClient.login(courierCredsWithoutPassword);
        int statusCode = loginResponse.extract().statusCode();
        String messageResponse = loginResponse.extract().path("message");
        assertEquals(400, statusCode);
        assertEquals("Недостаточно данных для входа", messageResponse);
    }

    @Test
    @DisplayName("Некорректные данные авторизации")
    @Description("Проверяем невозможность входа в систему с некорректными данными авторизации")
    public  void  courierIncorrectCreds(){
        ValidatableResponse loginResponse = courierClient.login(courierIncorrectCreds);
        int statusCode = loginResponse.extract().statusCode();
        String messageResponse = loginResponse.extract().path("message");
        assertEquals(404, statusCode);
        assertEquals("Учетная запись не найдена", messageResponse);
    }
}
