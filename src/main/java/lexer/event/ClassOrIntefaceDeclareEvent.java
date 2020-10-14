package lexer.event;

import lombok.Data;

@Data
public class ClassOrIntefaceDeclareEvent extends ContainerElementEvent{
    public ClassOrIntefaceDeclareEvent(String text, int startLine, int endLine, String bodyText) {
        super(text,startLine,endLine,bodyText);
    }
}
