package pl.piomin.quarkus.ai.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import pl.piomin.quarkus.ai.model.Share;

@ApplicationScoped
public class ShareRepository implements PanacheRepository<Share> {
}
