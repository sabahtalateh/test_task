package sabah.test.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import sabah.test.model.Command;
import sabah.test.repository.CommandRepository;

import java.time.LocalDateTime;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

@Service
public class CommandService implements CommandServiceInterface {
    private final CommandRepository commandRepository;

    public CommandService(CommandRepository commandRepository) {
        this.commandRepository = commandRepository;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Command storeCommand(String cmd, LocalDateTime requestedAt) {
        var requestedMinute = requestedAt.withSecond(0).withNano(0);
        var commandAtMinute = commandRepository.findByCommandAndRequestedMinute(cmd, requestedMinute);
        Command command;
        if (commandAtMinute.isEmpty()) {
            command = new Command();
            command.setCommand(cmd);
            command.setRequestedMinute(requestedMinute);
        } else {
            command = commandAtMinute.get();
        }
        command.increaseCount();
        return commandRepository.save(command);
    }

    public Map<LocalDateTime, Map<String, Integer>> statistic() {
        Map<LocalDateTime, Map<String, Integer>> commandsByMinutes = new HashMap<>();

        var commands = commandRepository.findAllByOrderByRequestedMinuteAsc();
        for (var command : commands) {
            var requestedMinute = command.getRequestedMinute();
            var commandsBySingleMinute = commandsByMinutes.get(requestedMinute);
            if (null == commandsBySingleMinute) {
                commandsByMinutes.put(requestedMinute, new HashMap<>());
                commandsBySingleMinute = commandsByMinutes.get(requestedMinute);
            }
            commandsBySingleMinute.put(command.getCommand(), command.getCount());
        }
        return commandsByMinutes;
    }

    public Map.Entry<LocalDateTime, Map<String, Integer>> getStatisticForPrevMinute(LocalDateTime time) {
        var prevMinute = time.withSecond(0).withNano(0).minusMinutes(1);
        var commands = commandRepository.findAllByRequestedMinute(prevMinute);
        Map<String, Integer> commandsMap = new HashMap<>();
        for (Command c : commands) {
            commandsMap.put(c.getCommand(), c.getCount());
        }
        return new AbstractMap.SimpleEntry<>(prevMinute, commandsMap);
    }
}
