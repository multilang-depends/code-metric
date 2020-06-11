package lexer.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NewBlockCommentEvent {
   int lineNumber;
   String text;
}
