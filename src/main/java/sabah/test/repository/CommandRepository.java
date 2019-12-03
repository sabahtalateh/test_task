package sabah.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sabah.test.model.Command;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CommandRepository extends JpaRepository<Command, Long> {
    List<Command> findAllByOrderByRequestedMinuteAsc();

    Optional<Command> findByCommandAndRequestedMinute(String command, LocalDateTime requestedMinute);

    List<Command> findAllByRequestedMinute(LocalDateTime requestedMinute);
}

