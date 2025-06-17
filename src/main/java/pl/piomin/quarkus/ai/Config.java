package pl.piomin.quarkus.ai;

import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import dev.langchain4j.store.memory.chat.InMemoryChatMemoryStore;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.inject.Produces;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

@ApplicationScoped
public class Config {

    private static final Logger LOG = Logger.getLogger(Config.class);

    @ConfigProperty(name = "ai.model.provider", defaultValue = "openai")
    String modelProvider;

//    @Produces
//    public ChatMemoryStore chatMemoryStore() {
//        return new InMemoryChatMemoryStore();
//    }
//
//    @Produces
//    public ChatMemoryProvider chatMemoryProvider(ChatMemoryStore chatMemoryStore) {
//        return memoryId -> MessageWindowChatMemory.builder()
//                .chatMemoryStore(chatMemoryStore)
//                .maxMessages(20)
//                .id(memoryId)
//                .build();
//    }

    void onStart(@Observes StartupEvent ev) {
        LOG.info("===========================================");
        LOG.info("Quarkus AI Showcase Application Starting");
        LOG.info("Selected AI Model Provider: " + modelProvider.toUpperCase());
        
        switch (modelProvider.toLowerCase()) {
            case "openai":
                LOG.info("Using OpenAI GPT models");
                LOG.info("Ensure OPENAI_API_KEY environment variable is set");
                break;
            case "mistral":
                LOG.info("Using Mistral AI models");
                LOG.info("Ensure MISTRAL_API_KEY environment variable is set");
                break;
            case "ollama":
                LOG.info("Using Ollama local models");
                LOG.info("Ensure Ollama is running on configured URL");
                break;
            default:
                LOG.warn("Unknown model provider: " + modelProvider);
                LOG.warn("Defaulting to OpenAI. Valid options: openai, mistral, ollama");
        }
        
        LOG.info("Application ready! Available endpoints:");
        LOG.info("  GET /api/persons/generate - Generate 10 persons");
        LOG.info("  GET /api/persons/{id} - Get person by ID with memory");
        LOG.info("===========================================");
    }
}