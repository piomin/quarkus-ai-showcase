package pl.piomin.quarkus.ai;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.*;
import pl.piomin.quarkus.ai.api.external.DailyStockData;
import pl.piomin.quarkus.ai.api.external.Meta;
import pl.piomin.quarkus.ai.api.external.StockData;
import pl.piomin.quarkus.ai.api.external.client.StockDataClient;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class WalletControllerTest {

    @InjectMock
    @RestClient
    StockDataClient stockDataClient;

    @BeforeEach
    void setUp() {
        // Mock the stock data responses
        StockData aaplStockData = createMockStockData("AAPL", "150.25");
        StockData amznStockData = createMockStockData("AMZN", "120.50");
        StockData metaStockData = createMockStockData("META", "250.75");
        StockData msftStockData = createMockStockData("MSFT", "300.00");

        // Mock the stock data client responses
        when(stockDataClient.getStockData(eq("AAPL"), anyString(), anyString(), anyInt()))
            .thenReturn(aaplStockData);
        when(stockDataClient.getStockData(eq("AMZN"), anyString(), anyString(), anyInt()))
            .thenReturn(amznStockData);
        when(stockDataClient.getStockData(eq("META"), anyString(), anyString(), anyInt()))
            .thenReturn(metaStockData);
        when(stockDataClient.getStockData(eq("MSFT"), anyString(), anyString(), anyInt()))
            .thenReturn(msftStockData);
    }

    private StockData createMockStockData(String symbol, String price) {
        DailyStockData dailyData = new DailyStockData();
        dailyData.setDatetime("2023-01-01");
        dailyData.setOpen(price);
        dailyData.setHigh(price);
        dailyData.setLow(price);
        dailyData.setClose(price);
        dailyData.setVolume("1000");

        StockData stockData = new StockData();
        stockData.setMeta(new Meta(symbol));
        stockData.setValues(List.of(dailyData));
        return stockData;
    }

    @Test
    @Order(1)
    void testCalculateWalletValueWithTools() {
        given()
          .when().get("/wallet/with-tools")
          .then().statusCode(200)
                 .contentType(ContentType.TEXT)
                 .body(notNullValue())
                 .body(not(emptyString()));
    }

    @Test
    @Order(2)
    void testCalculateHighestWalletValue() {
        given()
          .pathParam("days", 7)
          .when().get("/wallet/highest-day/{days}")
          .then().statusCode(200)
                 .contentType(ContentType.TEXT)
                 .body(notNullValue())
                 .body(not(emptyString()));
    }
}