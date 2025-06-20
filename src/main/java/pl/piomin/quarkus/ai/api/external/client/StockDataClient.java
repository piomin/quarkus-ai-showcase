package pl.piomin.quarkus.ai.api.external.client;

import io.quarkus.rest.client.reactive.ClientQueryParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.reactive.RestQuery;
import pl.piomin.quarkus.ai.api.external.StockData;

@RegisterRestClient(configKey = "stock-api")
public interface StockDataClient {

    @GET
    @Path("/time_series")
    StockData getStockData(@RestQuery String symbol,
                           @RestQuery String apikey,
                           @RestQuery String interval,
                           @RestQuery int outputsize);
}
