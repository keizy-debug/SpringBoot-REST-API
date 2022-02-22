package net.artnet.securitySeries.repo;

import net.artnet.securitySeries.model.Book;
import net.artnet.securitySeries.model.Lend;
import net.artnet.securitySeries.model.LendStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LendRepo extends JpaRepository<Lend, Long> {
    Optional<Lend> findByBookAndStatus(Book book, LendStatus burrowed);
}
