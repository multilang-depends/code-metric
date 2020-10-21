package lexer.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NewStmtEvent extends CodeElementEvent {
   String text;
}
