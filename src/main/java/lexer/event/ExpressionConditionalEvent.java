package lexer.event;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExpressionConditionalEvent extends  CodeElementEvent {
    String type;
    public ExpressionConditionalEvent(String text) {
        type = text;
    }
}
