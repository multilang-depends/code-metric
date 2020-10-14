package lexer.event;

import lombok.Data;

@Data
public class LeaveLastContainerEvent extends CodeElementEvent{
    private final String name;
    public LeaveLastContainerEvent(String text) {
        this.name = text;
    }
}
