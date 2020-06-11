/*
MIT License

Copyright (c) 2018-2019 Gang ZHANG

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package metric.parser.java;

import java.io.IOException;

import depends.extractor.java.JavaLexer;
import depends.extractor.java.JavaParser;
import lexer.event.LexerEventCenter;
import metric.measure.MetricContext;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.atn.LexerATNSimulator;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.tree.ParseTreeWalker;




public class JavaFileParser implements metric.parser.FileParser{
	private final MetricContext context;
	private String fileFullPath;
	public JavaFileParser(String fileFullPath, MetricContext context) {
        this.fileFullPath = fileFullPath;
        this.context =context;
	}

	@Override
	public void parse() throws IOException {
		LexerEventCenter.getInstance().notifyNewFile(fileFullPath, "java");
		CharStream input = CharStreams.fromFileName(fileFullPath);
		JavaLexer lexer = new JavaLexer(input);
        lexer.setInterpreter(new LexerATNSimulator(lexer, lexer.getATN(), lexer.getInterpreter().decisionToDFA, new PredictionContextCache()));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JavaParser parser = new JavaParser(tokens);
        ParserATNSimulator interpreter = new ParserATNSimulator(parser, parser.getATN(), parser.getInterpreter().decisionToDFA, new PredictionContextCache());
        parser.setInterpreter(interpreter);
        JavaListener bridge = new JavaListener(fileFullPath,context);
	    ParseTreeWalker walker = new ParseTreeWalker();
	    try {
	    	walker.walk(bridge, parser.compilationUnit());
			interpreter.clearDFA();

	    }catch (Exception e) {
	    	System.err.println("error encountered during parse..." );
	    	e.printStackTrace();
	    }
	    lexer.foundNewLine();
	    LexerEventCenter.getInstance().notifyDoneFile(fileFullPath);
    }
	
}
