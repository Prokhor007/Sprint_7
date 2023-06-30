import com.example.order.OrderClient;
import com.example.order.OrderCreate;
import com.example.order.OrderGenerator;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class OrderTest {
    private OrderClient orderClient;
    private OrderCreate order;
    int track;
    int statusCode;

    public OrderTest(OrderCreate order, int statusCode){
        this.order = order;
        this.statusCode = statusCode;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][]{
                {OrderGenerator.orderWithGrey(), 201},
                {OrderGenerator.orderWithBlack(), 201},
                {OrderGenerator.orderWithoutColour(), 201},
                {OrderGenerator.orderWithTwoColours(), 201}
        };
    }

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @After
    public void cleanUp(){
        if (track > 0)
            orderClient.deleteOrder(track);
    }

    @Test
    @DisplayName("Создание заказа")
    @Description("Проверяем, что запрос возвращает правильные код ответ и тело ok: true")
    public void orderCreated(){
        ValidatableResponse responseCreate = orderClient.createOrder(order);
        int actualStatusCode = responseCreate.extract().statusCode();
        track = responseCreate.extract().path("track");
        assertThat(track, notNullValue());
        assertEquals(statusCode,actualStatusCode);
    }
}
