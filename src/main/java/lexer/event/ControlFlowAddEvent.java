package lexer.event;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ControlFlowAddEvent extends  CodeElementEvent {
    int flowAdding;
    public ControlFlowAddEvent(int flowAdding) {
        this.flowAdding = flowAdding;
    }
}
