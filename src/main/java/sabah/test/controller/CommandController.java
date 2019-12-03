package sabah.test.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import sabah.test.model.Command;
import sabah.test.service.CommandService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@RestController
public class CommandController {
    private final CommandService commandService;

    public CommandController(CommandService commandService) {
        this.commandService = commandService;
    }

    @PostMapping("/command/{command}")
    public Command store(@PathVariable String command) {
        return commandService.storeCommand(command, LocalDateTime.now());
    }

    @GetMapping("/statistic")
    public SseEmitter statistic() {
        var executor = Executors.newSingleThreadScheduledExecutor();
        var emitter = new SseEmitter();

        executor.scheduleAtFixedRate(() -> {
            try {
                emitter.send(commandService.getStatisticForPrevMinute(LocalDateTime.now()), MediaType.APPLICATION_JSON);
            } catch (IOException e) {
                executor.shutdownNow();
            }
        }, 0, 60, TimeUnit.SECONDS);
        emitter.onCompletion(executor::shutdown);
        return emitter;
    }

    @GetMapping("/all-statistic")
    public Map<LocalDateTime, Map<String, Integer>> allStatistic() {
        return commandService.statistic();
    }
}
