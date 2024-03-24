package GlobalChat;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GlobalChatRepository extends JpaRepository<GlobalChatMessage, Long>
{

}