package pl.piomin.quarkus.ai.service;

import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import pl.piomin.quarkus.ai.tools.ShareTools;
import pl.piomin.quarkus.ai.tools.StockTools;

@RegisterAiService(tools = {StockTools.class, ShareTools.class})
public interface WalletAiService {

    @UserMessage("""
    What’s the current value in dollars of my wallet based on the latest stock daily prices ?
    """)
    String calculateWalletValueWithTools();

    @UserMessage("""
    On which day during last {days} days my wallet had the highest value in dollars based on the historical daily stock prices ?
    """)
    String calculateHighestWalletValue(int days);
}
