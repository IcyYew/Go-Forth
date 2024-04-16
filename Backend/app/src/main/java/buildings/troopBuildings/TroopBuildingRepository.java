package buildings.troopBuildings;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TroopBuildingRepository extends JpaRepository<TroopBuildingManager, Integer> {
}
