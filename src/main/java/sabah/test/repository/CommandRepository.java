package sabah.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sabah.test.model.Command;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CommandRepository extends JpaRepository<Command, Long> {
    List<Command> findAllByOrderByInsertedMinuteAsc();

    Command findByCommandAndInsertedMinute(String command, LocalDateTime insertedMinute);
}

