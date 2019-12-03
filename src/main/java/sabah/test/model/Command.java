package sabah.test.model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "commands", indexes = {
        @Index(columnList = "command, requested_minute", name = "command_requested_at_minute_idx"),
})
public class Command implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "command", nullable = false, updatable = false)
    private String command;

    @Column(name = "requested_minute", nullable = false, updatable = false)
    private LocalDateTime requestedMinute;

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

    public LocalDateTime getRequestedMinute() {
        return requestedMinute;
    }

    public void setRequestedMinute(LocalDateTime requestedMinute) {
        this.requestedMinute = requestedMinute;
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
