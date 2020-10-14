package lexer.event;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MethodDeclareEvent extends ContainerElementEvent{
    public MethodDeclareEvent(String text, int startLine, int endLine, String bodyText) {
        super(text,startLine,endLine,bodyText);
    }
}
