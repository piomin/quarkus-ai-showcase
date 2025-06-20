package pl.piomin.quarkus.ai.tools;

import dev.langchain4j.agent.tool.Tool;
import jakarta.enterprise.context.ApplicationScoped;
import pl.piomin.quarkus.ai.model.Share;
import pl.piomin.quarkus.ai.repository.ShareRepository;

import java.util.List;

@ApplicationScoped
public class ShareTools {

    private ShareRepository shareRepository;

    public ShareTools(ShareRepository shareRepository) {
        this.shareRepository = shareRepository;
    }

    @Tool("Return number of shares for each company in my wallet")
    public List<Share> getNumberOfShares() {
        return shareRepository.findAll().list();
    }
}
