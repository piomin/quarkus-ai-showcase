package pl.piomin.quarkus.ai.tools;

import dev.langchain4j.agent.tool.Tool;
import jakarta.enterprise.context.ApplicationScoped;
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

@ApplicationScoped
public class StockTools {

    private Logger log;
    private StockDataClient stockDataClient;

    public StockTools(@RestClient StockDataClient stockDataClient, Logger log) {
        this.stockDataClient = stockDataClient;
        this.log = log;
    }
    
    @ConfigProperty(name = "STOCK_API_KEY", defaultValue = "none")
    String apiKey;

    @Tool("Return latest stock prices for a given company")
    public StockResponse getLatestStockPrices(String company) {
        log.infof("Get stock prices for: %s", company);
        StockData data = stockDataClient.getStockData(company, apiKey, "1min", 1);
        DailyStockData latestData = data.getValues().get(0);
        log.infof("Get stock prices (%s) -> %s", company, latestData.getClose());
        return new StockResponse(Float.parseFloat(latestData.getClose()));
    }

    @Tool("Return historical daily stock prices for a given company")
    public List<DailyShareQuote> getHistoricalStockPrices(String company, int days) {
        log.infof("Get historical stock prices: %s for %d days", company, days);
        StockData data = stockDataClient.getStockData(company, apiKey, "1min", days);
        return data.getValues().stream()
                .map(d -> new DailyShareQuote(company, Float.parseFloat(d.getClose()), d.getDatetime()))
                .toList();
    }

}
