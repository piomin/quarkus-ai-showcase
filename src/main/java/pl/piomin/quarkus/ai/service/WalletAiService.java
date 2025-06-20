package pl.piomin.quarkus.ai.service;

import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import io.quarkiverse.langchain4j.guardrails.OutputGuardrails;
import pl.piomin.quarkus.ai.tools.ShareTools;
import pl.piomin.quarkus.ai.tools.StockTools;
import pl.piomin.quarkus.ai.validate.WalletGuardrail;

@RegisterAiService(tools = {StockTools.class, ShareTools.class})
public interface WalletAiService {

    @UserMessage("""
    What’s the current value in dollars of my wallet based on the latest stock daily prices ?
    
    Return subtotal value in dollars for each company in my wallet.
    In the end, return the total value in dollars wrapped by ***.
    """)
    @OutputGuardrails(WalletGuardrail.class)
    String calculateWalletValueWithTools();

    @UserMessage("""
    On which day during last {days} days my wallet had the highest value in dollars based on the historical daily stock prices ?
    """)
    String calculateHighestWalletValue(int days);
}
