package buildings.resourcebuildings;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceBuildingRepository extends JpaRepository<ResourceBuildingManager, Integer> {
}
