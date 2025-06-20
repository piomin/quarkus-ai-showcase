package pl.piomin.quarkus.ai.resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import pl.piomin.quarkus.ai.service.WalletAiService;

@Path("/wallet")
public class WalletController {

    private final WalletAiService walletAiService;

    public WalletController(WalletAiService walletAiService) {
        this.walletAiService = walletAiService;
    }

    @GET
    @Path("/with-tools")
    String calculateWalletValueWithTools() {
        return walletAiService.calculateWalletValueWithTools();
    }

    @GET
    @Path("/highest-day/{days}")
    String calculateHighestWalletValue(int days) {
        return walletAiService.calculateHighestWalletValue(days);
    }

}
