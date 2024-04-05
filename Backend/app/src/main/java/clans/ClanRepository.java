package clans;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Clan repository interface for storing based on ID.
 * @author Michael Geltz
 */
@Repository
public interface ClanRepository extends JpaRepository<Clan, Integer> {
}
