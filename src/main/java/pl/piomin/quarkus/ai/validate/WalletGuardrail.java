package pl.piomin.quarkus.ai.validate;

import dev.langchain4j.data.message.AiMessage;
import io.quarkiverse.langchain4j.guardrails.OutputGuardrail;
import io.quarkiverse.langchain4j.guardrails.OutputGuardrailResult;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ApplicationScoped
public class WalletGuardrail implements OutputGuardrail {

    Pattern pattern = Pattern.compile("\\*\\*\\*(.*?)\\*\\*\\*");

    private Logger log;
	
    @Inject
    public WalletGuardrail(Logger log) {
        this.log = log;
    }

    @Override
    public OutputGuardrailResult validate(AiMessage responseFromLLM) {
        try {
            Matcher matcher = pattern.matcher(responseFromLLM.text());
            if (matcher.find()) {
                String amount = matcher.group(1);
                log.infof("Extracted amount: %s", amount);
                if (amount.startsWith("$")) {
                    return success();
                }
            }
        } catch (Exception e) {
            return reprompt("Invalid text format", e, "Make sure you return a valid requested text");
        }
        return failure("Total amount not found");
    }
}
