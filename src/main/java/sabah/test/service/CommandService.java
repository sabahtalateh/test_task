package sabah.test.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import sabah.test.model.Command;
import sabah.test.repository.CommandRepository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommandService {
    private final CommandRepository commandRepository;

    public CommandService(CommandRepository commandRepository) {
        this.commandRepository = commandRepository;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Command storeCommand(String cmd) {
        LocalDateTime currentMinute = LocalDateTime.now().withSecond(0).withNano(0);

        Command command = commandRepository.findByCommandAndInsertedMinute(cmd, currentMinute);
        if (null == command) {
            command = new Command();
            command.setCommand(cmd);
            command.setInsertedMinute(currentMinute);
        }
        command.increaseCount();
        return commandRepository.save(command);
    }

    public Map<LocalDateTime, Map<String, Integer>> statistic() {
        Map<LocalDateTime, Map<String, Integer>> commandsByMinutes = new HashMap<>();

        List<Command> commands = commandRepository.findAllByOrderByInsertedMinuteAsc();
        for (Command command : commands) {
            LocalDateTime insertedMinute = command.getInsertedMinute();
            Map<String, Integer> commandsBySingleMinute = commandsByMinutes.get(insertedMinute);
            if (null == commandsBySingleMinute) {
                commandsByMinutes.put(insertedMinute, new HashMap<>());
                commandsBySingleMinute = commandsByMinutes.get(insertedMinute);
            }
            commandsBySingleMinute.put(command.getCommand(), command.getCount());
        }
        return commandsByMinutes;
    }
}
