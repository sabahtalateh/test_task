package sabah.test.model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "commands", indexes = {
        @Index(columnList = "command, inserted_minute", name = "command_inserted_at_minute_idx"),
})
public class Command implements Serializable {
    @Id
    @GeneratedValue(generator = "command_generator")
    @SequenceGenerator(name = "command_generator", sequenceName = "command_sequence")
    private Long id;

    @NotBlank
    @Column(name = "command", nullable = false, updatable = false)
    private String command;

    @Column(name = "inserted_minute", nullable = false, updatable = false)
    private LocalDateTime insertedMinute;

    @Column(name = "count", nullable = false)
    private int count = 0;

    public Long getId() {
        return id;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public LocalDateTime getInsertedMinute() {
        return insertedMinute;
    }

    public void setInsertedMinute(LocalDateTime insertedMinute) {
        this.insertedMinute = insertedMinute;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void increaseCount() {
        this.count++;
    }
}
