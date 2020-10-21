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

import lexer.event.*;
import metric.extractor.java.JavaParser;
import metric.extractor.java.JavaParserBaseListener;
import metric.measure.MetricContext;

public class JavaListener extends JavaParserBaseListener {



	public JavaListener(String fileFullPath, MetricContext context) {

	}

	@Override
	public void enterClassDeclaration(JavaParser.ClassDeclarationContext ctx) {
		super.enterClassDeclaration(ctx);
		LexerEventCenter.getInstance().notifyEvent(new ClassOrIntefaceDeclareEvent(ctx.IDENTIFIER().getText(),
				ctx.start.getLine(),ctx.stop.getLine(),ctx.getText()));
	}

	@Override
	public void exitClassDeclaration(JavaParser.ClassDeclarationContext ctx) {
		super.exitClassDeclaration(ctx);
		LexerEventCenter.getInstance().notifyEvent(new LeaveLastContainerEvent(ctx.IDENTIFIER().getText()));
	}

	@Override
	public void enterInterfaceDeclaration(JavaParser.InterfaceDeclarationContext ctx) {
		LexerEventCenter.getInstance().notifyEvent(new ClassOrIntefaceDeclareEvent(ctx.IDENTIFIER().getText(),
				ctx.start.getLine(),ctx.stop.getLine(),ctx.getText()));
		super.enterInterfaceDeclaration(ctx);
	}

	@Override
	public void exitInterfaceDeclaration(JavaParser.InterfaceDeclarationContext ctx) {
		super.exitInterfaceDeclaration(ctx);
		LexerEventCenter.getInstance().notifyEvent(new LeaveLastContainerEvent(ctx.IDENTIFIER().getText()));
	}

	@Override
	public void enterAnnotationTypeDeclaration(JavaParser.AnnotationTypeDeclarationContext ctx) {
		LexerEventCenter.getInstance().notifyEvent(new ClassOrIntefaceDeclareEvent(ctx.IDENTIFIER().getText(),
				ctx.start.getLine(),ctx.stop.getLine(),ctx.getText()));
		super.enterAnnotationTypeDeclaration(ctx);
	}

	@Override
	public void exitAnnotationTypeDeclaration(JavaParser.AnnotationTypeDeclarationContext ctx) {
		LexerEventCenter.getInstance().notifyEvent(new LeaveLastContainerEvent(ctx.IDENTIFIER().getText()));
		super.exitAnnotationTypeDeclaration(ctx);
	}


	@Override
	public void enterEnumDeclaration(JavaParser.EnumDeclarationContext ctx) {
		LexerEventCenter.getInstance().notifyEvent(new ClassOrIntefaceDeclareEvent(ctx.IDENTIFIER().getText(),
				ctx.start.getLine(),ctx.stop.getLine(),ctx.getText()));
		super.enterEnumDeclaration(ctx);
	}

	@Override
	public void exitEnumDeclaration(JavaParser.EnumDeclarationContext ctx) {
		LexerEventCenter.getInstance().notifyEvent(new LeaveLastContainerEvent(ctx.IDENTIFIER().getText()));
		super.exitEnumDeclaration(ctx);
	}

	@Override
	public void enterMethodDeclaration(JavaParser.MethodDeclarationContext ctx) {
		LexerEventCenter.getInstance().notifyEvent(new MethodDeclareEvent(ctx.IDENTIFIER().getText(),
				ctx.start.getLine(),ctx.stop.getLine(),ctx.getText()));
		super.enterMethodDeclaration(ctx);
	}

	@Override
	public void exitMethodDeclaration(JavaParser.MethodDeclarationContext ctx) {
		LexerEventCenter.getInstance().notifyEvent(new LeaveLastContainerEvent(ctx.IDENTIFIER().getText()));
		super.exitMethodDeclaration(ctx);
	}

	@Override
	public void enterStatement(JavaParser.StatementContext ctx) {
		LexerEventCenter.getInstance().notifyEvent(new NewStmtEvent(ctx.getText()));
		if (ctx.IF()!=null){
			LexerEventCenter.getInstance().notifyEvent(new ControlFlowAddEvent(ctx.ELSE()==null?1:2));
		}
		if (ctx.FOR()!=null){
			LexerEventCenter.getInstance().notifyEvent(new ControlFlowAddEvent(2));
		}
		if (ctx.WHILE()!=null){
			LexerEventCenter.getInstance().notifyEvent(new ControlFlowAddEvent(2));
		}
		if (ctx.SWITCH()!=null){
			LexerEventCenter.getInstance().notifyEvent(new ControlFlowAddEvent(1));
		}
		if (ctx.BREAK()!=null && ctx.IDENTIFIER()!=null){
			LexerEventCenter.getInstance().notifyEvent(new ControlFlowAddEvent(1));
		}
		if (ctx.CONTINUE()!=null && ctx.IDENTIFIER()!=null){
			LexerEventCenter.getInstance().notifyEvent(new ControlFlowAddEvent(1));
		}
		if (ctx.catchClause()!=null){
			LexerEventCenter.getInstance().notifyEvent(new ControlFlowAddEvent(ctx.catchClause().size()));
		}

		super.enterStatement(ctx);
	}

	@Override
	public void enterVariableDeclarator(JavaParser.VariableDeclaratorContext ctx) {
		LexerEventCenter.getInstance().notifyEvent(new NewStmtEvent(ctx.getText()));
		super.enterVariableDeclarator(ctx);
	}

	@Override
	public void enterConstantDeclarator(JavaParser.ConstantDeclaratorContext ctx) {
		LexerEventCenter.getInstance().notifyEvent(new NewStmtEvent(ctx.getText()));
		super.enterConstantDeclarator(ctx);
	}

	@Override
	public void enterExpression(JavaParser.ExpressionContext ctx) {
		if (ctx.bop!=null){
			if (ctx.bop.getText().equals("||") ||
				ctx.bop.getText().equals("&&"))
				if (!(ctx.parent instanceof JavaParser.ExpressionContext)){
					LexerEventCenter.getInstance().notifyEvent(new ControlFlowAddEvent(new ControlFlowCount(ctx).count()));
				}
			LexerEventCenter.getInstance().notifyEvent(new ExpressionConditionalEvent(ctx.bop.getText()));
		}
		super.enterExpression(ctx);
	}

}
