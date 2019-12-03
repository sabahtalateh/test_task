package sabah.test.service;

import sabah.test.model.Command;

import java.time.LocalDateTime;
import java.util.Map;

public interface CommandServiceInterface {
    Command storeCommand(String cmd, LocalDateTime requestedAt);

    Map<LocalDateTime, Map<String, Integer>> statistic();

    Map.Entry<LocalDateTime, Map<String, Integer>> getStatisticForPrevMinute(LocalDateTime minute);
}
