package sabah.test;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import sabah.test.model.Command;
import sabah.test.repository.CommandRepository;
import sabah.test.service.CommandService;

import java.time.LocalDateTime;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
class TestApplicationTests {

    @Autowired
    private CommandRepository commandRepository;

    @Autowired
    private CommandService commandService;

    @Test
    public void testCorrectCommandsCount() {
        // Insert two cmd_1 commands at 10:15:17 and 10:15:20
        commandService.storeCommand("cmd_1", LocalDateTime.of(2019, 12, 3, 10, 15, 17));
        commandService.storeCommand("cmd_1", LocalDateTime.of(2019, 12, 3, 10, 15, 20));

        // Check cmd_1 count for 10:16 equals to 2
        LocalDateTime requestedMinute = LocalDateTime.of(2019, 12, 3, 10, 15, 0);
        Optional<Command> cmd_1 = commandRepository.findByCommandAndRequestedMinute("cmd_1", requestedMinute);
        Assert.assertEquals(cmd_1.get().getCount(), 2);

        // Then insert one cmd_1 command at 10:16:20 and one cmd_2 command at 10:16:43
        commandService.storeCommand("cmd_1", LocalDateTime.of(2019, 12, 3, 10, 16, 20));
        commandService.storeCommand("cmd_2", LocalDateTime.of(2019, 12, 3, 10, 16, 43));

        // Check cmd_1 count for 10:16 equals to 1
        requestedMinute = LocalDateTime.of(2019, 12, 3, 10, 16, 0);
        cmd_1 = commandRepository.findByCommandAndRequestedMinute("cmd_1", requestedMinute);
        Optional<Command> cmd_2 = commandRepository.findByCommandAndRequestedMinute("cmd_2", requestedMinute);
        Assert.assertEquals(cmd_1.get().getCount(), 1);
        Assert.assertEquals(cmd_2.get().getCount(), 1);
    }
}

