package buildings.troopBuildings;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Resource repository interface for storing based on ID.
 * @author Michael Geltz
 */
@Repository
public interface TroopBuildingRepository extends JpaRepository<TroopTrainingBuilding, Integer> {
}
