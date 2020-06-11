package lexer.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NewLineEvent {
   int lineNumber;
   String text;
}
