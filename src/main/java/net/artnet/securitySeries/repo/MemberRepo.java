package net.artnet.securitySeries.repo;

import net.artnet.securitySeries.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepo extends JpaRepository<Member, Long> {

}
