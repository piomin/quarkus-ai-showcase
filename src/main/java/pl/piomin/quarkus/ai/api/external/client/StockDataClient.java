package pl.piomin.quarkus.ai.api.external.client;

import io.quarkus.rest.client.reactive.ClientQueryParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import pl.piomin.quarkus.ai.api.external.StockData;

@RegisterRestClient(configKey = "stock-api")
public interface StockDataClient {

    @GET
    @Path("/time_series")
    @ClientQueryParam(name = "company", value = "{company}")
    @ClientQueryParam(name = "apikey", value = "{apikey}")
    @ClientQueryParam(name = "interval", value = "1min")
    @ClientQueryParam(name = "outputsize", value = "{outputsize}")
    StockData getStockData(String company, String apiKey, int outputsize);
}
