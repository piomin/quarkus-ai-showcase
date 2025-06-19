package pl.piomin.quarkus.ai.tools;

import dev.langchain4j.agent.tool.Tool;
import pl.piomin.quarkus.ai.model.Share;
import pl.piomin.quarkus.ai.repository.ShareRepository;

import java.util.List;

public class ShareTools {

    private ShareRepository shareRepository;

    public ShareTools(ShareRepository shareRepository) {
        this.shareRepository = shareRepository;
    }

    @Tool("Number of shares for each company in my wallet")
    public List<Share> getNumberOfShares() {
        return (List<Share>) shareRepository.findAll();
    }
}
