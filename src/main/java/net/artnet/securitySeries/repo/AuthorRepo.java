package net.artnet.securitySeries.repo;

import net.artnet.securitySeries.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepo extends JpaRepository<Author, Long> {

}
