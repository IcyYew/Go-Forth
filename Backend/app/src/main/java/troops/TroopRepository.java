package troops;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Troop repository interface for storing based on ID.
 * @author Michael Geltz
 */
@Repository
public interface TroopRepository extends JpaRepository<TroopManager, Integer> {
}
