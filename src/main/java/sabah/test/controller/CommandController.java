package sabah.test.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import sabah.test.model.Command;
import sabah.test.service.CommandService;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
public class CommandController {
    private final CommandService commandService;

    public CommandController(CommandService commandService) {
        this.commandService = commandService;
    }

    @PostMapping("/command/{command}")
    public Command store(@PathVariable String command) {
        return commandService.storeCommand(command);
    }

    @GetMapping("/statistic")
    public Map<LocalDateTime, Map<String, Integer>> list() {
        return commandService.statistic();
    }
}
