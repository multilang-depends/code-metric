package lexer.event;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public abstract class ContainerElementEvent extends CodeElementEvent {
    private String name  = null;
    private int startLine = 0;
    private int endLine = 0;
    private String bodyText = null;
    public ContainerElementEvent(String text, int startLine, int endLine, String bodyText) {
        this.name = text;
        this.startLine = startLine;
        this.endLine = endLine;
        this.bodyText = bodyText;
    }
}
