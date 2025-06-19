package pl.piomin.quarkus.ai.tools;

import dev.langchain4j.agent.tool.Tool;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;
import pl.piomin.quarkus.ai.api.DailyShareQuote;
import pl.piomin.quarkus.ai.api.StockResponse;
import pl.piomin.quarkus.ai.api.external.DailyStockData;
import pl.piomin.quarkus.ai.api.external.StockData;
import pl.piomin.quarkus.ai.api.external.client.StockDataClient;

import java.util.List;

public class StockTools {

    @Inject
    private Logger log;
    @Inject
    @RestClient
    private StockDataClient stockDataClient;
    @ConfigProperty(name = "${STOCK_API_KEY}", defaultValue = "none")
    String apiKey;

    @Tool("Latest stock prices for a givencompany")
    public StockResponse getLatestStockPrices(String company) {
        log.infof("Get stock prices for: %s", company);
        StockData data = stockDataClient.getStockData(company, apiKey);
        DailyStockData latestData = data.getValues().get(0);
        log.infof("Get stock prices (%s) -> %s", company, latestData.getClose());
        return new StockResponse(Float.parseFloat(latestData.getClose()));
    }

    @Tool("Historical daily stock prices for a given company")
    public List<DailyShareQuote> getHistoricalStockPrices(String company, int days) {
        log.infof("Get historical stock prices: %s for %d days", company, days);
        StockData data = stockDataClient.getStockData(company, apiKey);
        return data.getValues().stream()
                .map(d -> new DailyShareQuote(company, Float.parseFloat(d.getClose()), d.getDatetime()))
                .toList();
    }

}
