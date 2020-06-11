package lexer.event;

import java.util.Observable;

public class LexerEventCenter extends Observable {
    private  static  LexerEventCenter inst = null;
    public static LexerEventCenter getInstance(){
        if (inst==null)
            inst = new LexerEventCenter();
        return inst;
    }

    public void notifyNewLine(int lineNumber, String text){
        this.setChanged();
        this.notifyObservers(new NewLineEvent(lineNumber,text));
    }

    public void notifyNewFile(String fileFullPath, String lang) {
        this.setChanged();
        this.notifyObservers(new NewFileEvent(fileFullPath,lang));
    }

    public void notifyDoneFile(String fileFullPath) {
        this.setChanged();
        this.notifyObservers(new DoneFileEvent(fileFullPath));
    }

    public void notifyCommentLine(int lineNumber, String comment) {
        this.setChanged();
        this.notifyObservers(new NewBlockCommentEvent(lineNumber,comment));
    }
}
