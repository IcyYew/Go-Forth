package buildings;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Resource repository interface for storing based on ID.
 * @author Michael Geltz
 */
@Repository
public interface OtherBuildingRepository extends JpaRepository<OtherBuilding, Integer> {
}
