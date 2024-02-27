package troops;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TroopRepository extends JpaRepository<TroopTypes, Integer> {
}
