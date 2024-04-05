package Websockets;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClanChatMessageRepository extends JpaRepository<ClanChatMessage, Long> {
}
