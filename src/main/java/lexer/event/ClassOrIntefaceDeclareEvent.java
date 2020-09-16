package lexer.event;

import lombok.Data;

@Data
public class ClassOrIntefaceDeclareEvent extends CodeElementEvent{
    private final String name;
    public ClassOrIntefaceDeclareEvent(String text) {
        this.name = text;
    }
}
