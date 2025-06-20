package pl.piomin.quarkus.ai.resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import pl.piomin.quarkus.ai.service.WalletAiService;

@Path("/wallet")
@Produces(MediaType.TEXT_PLAIN)
public class WalletController {

    private final WalletAiService walletAiService;

    public WalletController(WalletAiService walletAiService) {
        this.walletAiService = walletAiService;
    }

    @GET
    @Path("/with-tools")
    public String calculateWalletValueWithTools() {
        return walletAiService.calculateWalletValueWithTools();
    }

    @GET
    @Path("/highest-day/{days}")
    public String calculateHighestWalletValue(int days) {
        return walletAiService.calculateHighestWalletValue(days);
    }

}
