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

import lexer.event.ClassOrIntefaceDeclareEvent;
import lexer.event.LexerEventCenter;
import metric.extractor.java.JavaParser;
import metric.extractor.java.JavaParserBaseListener;
import metric.measure.MetricContext;

public class JavaListener extends JavaParserBaseListener {


	private MetricContext context;

	public JavaListener(String fileFullPath, MetricContext context) {
		this.context = context;
	}

	@Override
	public void enterClassDeclaration(JavaParser.ClassDeclarationContext ctx) {
		super.enterClassDeclaration(ctx);
		LexerEventCenter.getInstance().notifyEvent(new ClassOrIntefaceDeclareEvent(ctx.IDENTIFIER().getText()));
	}

	@Override
	public void enterInterfaceDeclaration(JavaParser.InterfaceDeclarationContext ctx) {
		LexerEventCenter.getInstance().notifyEvent(new ClassOrIntefaceDeclareEvent(ctx.IDENTIFIER().getText()));
		super.enterInterfaceDeclaration(ctx);
	}

	@Override
	public void enterAnnotationTypeDeclaration(JavaParser.AnnotationTypeDeclarationContext ctx) {
		LexerEventCenter.getInstance().notifyEvent(new ClassOrIntefaceDeclareEvent(ctx.IDENTIFIER().getText()));
		super.enterAnnotationTypeDeclaration(ctx);
	}

	@Override
	public void enterEnumDeclaration(JavaParser.EnumDeclarationContext ctx) {
		LexerEventCenter.getInstance().notifyEvent(new ClassOrIntefaceDeclareEvent(ctx.IDENTIFIER().getText()));
		super.enterEnumDeclaration(ctx);
	}

	@Override
	public void enterMethodDeclaration(JavaParser.MethodDeclarationContext ctx) {
		LexerEventCenter.getInstance().notifyEvent(new ClassOrIntefaceDeclareEvent(ctx.IDENTIFIER().getText()));
		super.enterMethodDeclaration(ctx);
	}
}
